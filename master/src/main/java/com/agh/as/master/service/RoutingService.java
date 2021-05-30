package com.agh.as.master.service;

import com.agh.as.master.dto.request.CreateRouteForm;
import com.agh.as.master.model.Map;
import com.agh.as.master.model.Node;
import com.agh.as.master.model.RouteData;
import com.agh.as.master.repo.RouteRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoutingService {

    MapCache mapCache;
    RouteRepo routeRepo;


    public Mono<RouteData> startCreatingRoute(CreateRouteForm form) {
        Node closestStartNode = findClosestNode(form.getStartX(), form.getStartY());
        Node closestTargetNode = findClosestNode(form.getTargetX(), form.getTargetY());

        RouteData routeData = RouteData.builder().start(closestStartNode).target(closestTargetNode).build();
        return routeRepo.save(routeData);
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
}
