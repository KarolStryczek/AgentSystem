package com.agh.as.master.service;

import com.agh.as.master.consumer.AgentConsumer;
import com.agh.as.master.dto.consumer.AddAreaForm;
import com.agh.as.master.dto.consumer.NeighborDto;
import com.agh.as.master.dto.request.RegisterAgentForm;
import com.agh.as.master.model.AgentInstance;
import com.agh.as.master.model.Area;
import com.agh.as.master.model.Node;
import com.agh.as.master.repo.AgentInstanceRepo;
import com.agh.as.master.utils.AreaAllocator;
import com.agh.as.master.utils.LogUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RunningAgentsService {

    @Value("${min.agent.num}")
    Integer minRunningAgents;

    final AgentInstanceRepo agentInstanceRepo;
    final AgentConsumer agentConsumer;

    public Mono<AgentInstance> registerInstance(RegisterAgentForm form) {
        AgentInstance agentInstance = new AgentInstance(form.getInstanceId(), form.getHost());

        return agentInstanceRepo.save(agentInstance)
                .doOnSuccess(LogUtils::logSaveEntity);
    }


    public Flux<AgentInstance> getRunningAgentInstances() {
        return agentInstanceRepo.findAll()
                .doOnNext(LogUtils::logFoundEntity);
    }

    public Mono<Void> deleteFailedAgent(AgentInstance agentInstance) {
        return agentInstanceRepo.delete(agentInstance)
                .doOnSuccess(LogUtils::logDeleteEntity);
    }

    public Mono<Void> deleteFailedAgentWithAssignedArea(AgentInstance agentInstance) {
        return agentInstanceRepo.findAllByAreaIsNull()
                .next()
                .flatMap(freeAgent -> assignAreaForAgent(agentInstance, freeAgent))
                .flatMap(ignored -> deleteFailedAgent(agentInstance));
    }


    private Mono<AgentInstance> assignAreaForAgent(AgentInstance newAgent, AgentInstance failedAgent) {
        newAgent.setArea(failedAgent.getArea());
        return agentInstanceRepo.save(newAgent).doOnSuccess(LogUtils::logUpdateEntity);
    }

    public Mono<Void> deleteAll() {
        return agentInstanceRepo.deleteAll().doOnNext(LogUtils::logDeleteEntity);
    }

    Mono<AgentInstance> getAgentForNode(Integer start) {
        return agentInstanceRepo.findByArea_Nodes_IdContains(start);
    }

    @Scheduled(fixedDelay = 5000)
    public void allocateAreas() {
        List<Area> allocatedAreas = AreaAllocator.getAreasFormConfig();

        getRunningAgentInstances().count()
                .filter(count -> count >= minRunningAgents)
                .flatMap(ignored -> setAreasToAgents(allocatedAreas)).subscribe();
    }

    private Mono<Void> setAreasToAgents(List<Area> areaList) {
        log.info("Start allocating agents to areas [{}]", areaList.toString());


        return getRunningAgentInstances().collectList().flatMap(agentList -> assignAreasToAgents(areaList, agentList));
    }

    private Mono<Void> assignAreasToAgents(List<Area> areaList, List<AgentInstance> agentList) {
        List<AddAreaForm> addAreaForms = new ArrayList<>();
        for (Area area: areaList) {
            AddAreaForm areaForm = new AddAreaForm();
            areaForm.setId(area.getAreaId());
            areaForm.setNodes(area.getNodes().stream().map(Node::getId).collect(Collectors.toList()));
            List<NeighborDto> neighbors = new ArrayList<>();
            for (Node node: area.getNodes()) {
                List<Area> neighbourAreas = areaList.stream().filter(a -> !a.getAreaId().equals(area.getAreaId())).filter(a -> a.getNodes().contains(node)).collect(Collectors.toList());
                if (!neighbourAreas.isEmpty()) {
                    for (Area neighbourArea: neighbourAreas) {
                        neighbors.add(new NeighborDto(node.getId(), neighbourArea.getAreaId()));
                    }
                }
            }
            areaForm.setNeighbors(neighbors);
            addAreaForms.add(areaForm);
        }
        return Flux.fromIterable(addAreaForms)
                .doOnNext(addAreaForm -> {
                    String host = agentList.stream().filter(agent -> agent.getInstanceId().equals(addAreaForm.getId())).findFirst().get().getInstanceHost();
                    agentConsumer.setArea(host, addAreaForm).subscribe();
                }).then();
    }

}
