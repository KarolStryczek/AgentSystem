package com.agh.as.master.service;

import com.agh.as.master.consumer.AgentConsumer;
import com.agh.as.master.dto.consumer.RegisterRouteForm;
import com.agh.as.master.dto.request.UpdateAgentRouteForm;
import com.agh.as.master.dto.request.CreateRouteForm;
import com.agh.as.master.dto.request.UpdateRouteForm;
import com.agh.as.master.model.Map;
import com.agh.as.master.model.Node;
import com.agh.as.master.model.RouteData;
import com.agh.as.master.repo.RouteRepo;
import com.agh.as.master.utils.LogUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoutingService {

    MapCache mapCache;
    RouteRepo routeRepo;
    RunningAgentsService runningAgentsService;
    AgentConsumer agentConsumer;


    public Mono<RouteData> startCreatingRoute(CreateRouteForm form) {
        Node closestStartNode = findClosestNode(form.getStartX(), form.getStartY());
        Node closestTargetNode = findClosestNode(form.getTargetX(), form.getTargetY());

        RouteData routeData = RouteData.builder().start(closestStartNode).target(closestTargetNode).build();
        RegisterRouteForm registerRouteForm = new RegisterRouteForm(closestStartNode.getId(), closestTargetNode.getId());
        return routeRepo.save(routeData)
                .doOnSuccess(routeD -> {
                    LogUtils.logSaveEntity(routeD);
                    registerRouteForm.setId(routeD.getId());
                })
                .flatMap(ignored -> runningAgentsService.getAgentForNode(closestStartNode.getId()))
                .doOnSuccess(agent -> registerRouteForm.setCurrentAgent(agent.getInstanceId()))
                .flatMap(agent -> agentConsumer.startRoute(agent.getInstanceHost(), agent.getInstanceId().toString(), registerRouteForm))
                .thenReturn(routeData);
    }

    private Node findClosestNode(Float x, Float y) {
        Map map = mapCache.getMap("all");
        Node result = null;
        double min = Float.MAX_VALUE;
        for (Node node: map.getNodes()) {
            double current = Math.sqrt((node.getX()*node.getX()-x*x)+(node.getY()*node.getY()-y*y));
            if (current < min) {
                result = node;
                min = current;
            }
        }
        return result;
    }

    public Flux<Node> checkRouteStatus(String id) {
        return routeRepo.findById(id)
                .filter(routeData -> !Objects.isNull(routeData.getResult()) && !routeData.getResult().isEmpty())
                .flatMapMany(routeData -> Flux.fromIterable(routeData.getResult()));
    }

    public Mono<RouteData> updateRouteFinally(UpdateRouteForm form) {
        return routeRepo.findById(form.getId())
                .doOnSuccess(route -> route.setResult(form.getFinalRoute()))
                .flatMap(routeRepo::save)
                .doOnSuccess(LogUtils::logUpdateEntity);
    }

    public Mono<Void> updateRouteInAgent(UpdateAgentRouteForm form) {
        return runningAgentsService.getAgentForNode(form.getSource())
                .flatMap(agent -> agentConsumer.updateRoute(agent.getInstanceHost(), agent.getInstanceId().toString(), form));
    }
}
