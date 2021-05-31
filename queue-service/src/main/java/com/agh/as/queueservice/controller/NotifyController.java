package com.agh.as.queueservice.controller;

import com.agh.as.queueservice.dto.response.RouteResponse;
import com.agh.as.queueservice.model.Route;
import com.agh.as.queueservice.service.RouteService;
import com.agh.as.queueservice.utils.LogUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotifyController {

    RouteService routeService;

    @GetMapping("/agent/{id}/id")
    public Mono<RouteResponse> getRoutesForAgents(@PathVariable Integer id) {
        LogUtils.logGetController("getRoutesForAgents", "agentId", id.toString());
        return routeService.getRouteForAgent(id).onErrorReturn(new Route()).map(RouteResponse::new);
    }
}
