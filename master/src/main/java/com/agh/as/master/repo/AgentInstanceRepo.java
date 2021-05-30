package com.agh.as.master.repo;

import com.agh.as.master.model.AgentInstance;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AgentInstanceRepo extends ReactiveMongoRepository<AgentInstance, String> {
    Flux<AgentInstance> findAllByAreaIsNull();

}
