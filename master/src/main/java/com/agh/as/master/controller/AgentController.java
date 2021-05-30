package com.agh.as.master.controller;


import com.agh.as.master.dto.request.RegisterAgentForm;
import com.agh.as.master.model.AgentInstance;
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

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AgentInstance> registerInstance(@Valid @RequestBody RegisterAgentForm registerAgentForm){
        LogUtils.logGetRequestBody("registerInstance", registerAgentForm);
        return runningAgentsService.registerInstance(registerAgentForm);
    }
}
