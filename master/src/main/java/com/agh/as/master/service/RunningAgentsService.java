package com.agh.as.master.service;

import com.agh.as.master.dto.request.RegisterAgentForm;
import com.agh.as.master.model.AgentInstance;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RunningAgentsService {


    public Mono<AgentInstance> registerInstance(RegisterAgentForm registerAgentForm){
        return Mono.just(new AgentInstance(1, "http://localhost:8081"));
    }





}
