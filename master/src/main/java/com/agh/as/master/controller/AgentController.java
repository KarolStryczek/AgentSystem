package com.agh.as.master.controller;


import com.agh.as.master.dto.request.RegisterAgentForm;
import com.agh.as.master.dto.request.UpdateRouteForm;
import com.agh.as.master.model.AgentInstance;
import com.agh.as.master.service.RoutingService;
import com.agh.as.master.service.RunningAgentsService;
import com.agh.as.master.utils.LogUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agent")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AgentController {

    RunningAgentsService runningAgentsService;
    RoutingService routingService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AgentInstance> registerInstance(@Valid @RequestBody RegisterAgentForm registerAgentForm){
        LogUtils.logGetRequestBody("registerInstance", registerAgentForm);
        return runningAgentsService.registerInstance(registerAgentForm);
    }

    @PostMapping("/route/finish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> setFinalRoute(@RequestBody UpdateRouteForm updateRouteForm) {
        return routingService.updateRouteFinally(updateRouteForm).then();
    }
}
