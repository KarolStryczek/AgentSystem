package com.agh.as.master.service;

import com.agh.as.master.dto.request.RegisterAgentForm;
import com.agh.as.master.model.AgentInstance;
import com.agh.as.master.repo.AgentInstanceRepo;
import com.agh.as.master.utils.LogUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RunningAgentsService {

    AgentInstanceRepo agentInstanceRepo;

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




}
