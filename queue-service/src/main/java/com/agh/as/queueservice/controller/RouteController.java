package com.agh.as.queueservice.controller;

import com.agh.as.queueservice.dto.request.RegisterRouteForm;
import com.agh.as.queueservice.dto.request.UpdateRouteForm;
import com.agh.as.queueservice.service.RouteService;
import com.agh.as.queueservice.utils.LogUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/route")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RouteController {

    RouteService routeService;

    @PostMapping("/update")
    public Mono<Void> updateRoute(@RequestBody UpdateRouteForm form) {
        LogUtils.logGetRequestBody("updateRoute", form);
        return routeService.updateRoute(form);
    }

    @PostMapping("/register")
    public Mono<Void> registerRoute(@RequestBody RegisterRouteForm form) {
        LogUtils.logGetRequestBody("registerRoute", form);
        return routeService.registerRoute(form);
    }
}
