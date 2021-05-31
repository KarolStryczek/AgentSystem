package com.agh.as.queueservice.service;

import com.agh.as.queueservice.dto.request.RegisterRouteForm;
import com.agh.as.queueservice.dto.request.UpdateRouteForm;
import com.agh.as.queueservice.model.Node;
import com.agh.as.queueservice.model.Route;
import com.agh.as.queueservice.repo.RouteRepo;
import com.agh.as.queueservice.utils.LogUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.rmi.NoSuchObjectException;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RouteService {

    RouteRepo routeRepo;

    public Mono<Route> getRouteForAgent(Integer agentId) {
        return routeRepo.findByCurrentAgentAndIsProcessedFalseOrderByCreatedAtAsc(agentId).next()
                .switchIfEmpty(Mono.error(new NoSuchObjectException("route")))
                .doOnSuccess(route -> {
                    LogUtils.logFoundEntity(route);
                    route.setIsProcessed(true);
                })
                .flatMap(routeRepo::save)
                .doOnSuccess(LogUtils::logSaveEntity);
    }

    public Mono<Void> updateRoute(UpdateRouteForm form) {
        return routeRepo.findById(form.getId())
                .doOnSuccess(LogUtils::logFoundEntity)
                .flatMap(route -> updateCurrentRouteAndSave(route, form.getNewFragment(), form.getNewAgent()));
    }

    private Mono<Void> updateCurrentRouteAndSave(Route route, List<Node> newFragment, Integer newAgent) {
        newFragment.forEach(route::addNodeToRoute);
        route.setCurrent(newFragment.get(newFragment.size()-1));
        route.setCurrentAgent(newAgent);
        route.setIsProcessed(false);
        return routeRepo.save(route).doOnSuccess(LogUtils::logSaveEntity).then();
    }

    public Mono<Void> registerRoute(RegisterRouteForm form) {
        Route route = new Route();
        route.setId(form.getId());
        route.setIsProcessed(true);
        route.setCurrentAgent(form.getCurrentAgent());
        route.setStart(new Node(form.getStartNode()));
        route.setTarget(new Node(form.getTargetNode()));
        route.setCurrent(new Node(form.getStartNode()));

        return routeRepo.save(route).doOnSuccess(LogUtils::logSaveEntity).then();
    }
}
