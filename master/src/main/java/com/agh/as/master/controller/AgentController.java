package com.agh.as.master.controller;


import com.agh.as.master.dto.request.RegisterAgentForm;
import com.agh.as.master.service.RunningAgentsService;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/agent")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentController {

    RunningAgentsService runningAgentsService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> registerInstance(@Valid @RequestBody RegisterAgentForm registerAgentForm){
        return runningAgentsService.registerInstance(registerAgentForm).then();
    }
}
