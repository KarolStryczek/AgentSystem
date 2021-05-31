package com.agh.as.master.controller;

import com.agh.as.master.dto.request.UpdateAgentRouteForm;
import com.agh.as.master.dto.request.CreateRouteForm;
import com.agh.as.master.model.Area;
import com.agh.as.master.model.Node;
import com.agh.as.master.model.RouteData;
import com.agh.as.master.service.RoutingService;
import com.agh.as.master.utils.AreaAllocator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RouteController {

    RoutingService routingService;

    @GetMapping("/route/status/{id}/id")
    public Flux<Node> checkRouteStatus(@PathVariable String id) {
        return routingService.checkRouteStatus(id);
    }

    @PostMapping("/route")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RouteData> startCreatingRoute(@RequestBody CreateRouteForm createRouteForm) {
        return routingService.startCreatingRoute(createRouteForm);
    }

    @PostMapping("/agent/route")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> updateRouteInAgent(@RequestBody UpdateAgentRouteForm form) {
        return routingService.updateRouteInAgent(form);
    }

    @GetMapping("/route/test")
    public Mono<List<Area>> test() {
        return Mono.just(AreaAllocator.getAreasFormConfig());
    }
}
