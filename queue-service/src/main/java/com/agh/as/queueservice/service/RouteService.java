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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RouteService {

    RouteRepo routeRepo;

    public Mono<Route> getRouteForAgent(Integer agentId) {
        return routeRepo.findByCurrentAgentOrderByCreatedAtAsc(agentId).next().doOnSuccess(LogUtils::logFoundEntity);
    }

    public Mono<Void> updateRoute(UpdateRouteForm form) {
        return routeRepo.findById(form.getId())
                .doOnSuccess(LogUtils::logFoundEntity)
                .flatMap(route -> updateCurrentRouteAndSave(route, form.getNewFragment(), form.getNewAgent()));
    }

    private Mono<Void> updateCurrentRouteAndSave(Route route, List<Node> newFragment, Integer newAgent) {
        route.getCurrentRoute().addAll(newFragment);
        route.setCurrent(newFragment.get(0));
        route.setCurrentAgent(newAgent);
        return routeRepo.save(route).doOnSuccess(LogUtils::logSaveEntity).then();
    }

    public Mono<Void> registerRoute(RegisterRouteForm form) {
        Route route = new Route();
        route.setId(form.getId());
        route.setCurrentAgent(form.getCurrentAgent());
        route.setStart(new Node(form.getStartNode()));
        route.setStart(new Node(form.getTargetNode()));
        route.setCurrent(new Node(form.getStartNode()));

        return routeRepo.save(route).doOnSuccess(LogUtils::logSaveEntity).then();
    }
}
