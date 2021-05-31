package com.agh.as.master.repo;

import com.agh.as.master.model.AgentInstance;
import com.agh.as.master.model.Node;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AgentInstanceRepo extends ReactiveMongoRepository<AgentInstance, String> {
    Flux<AgentInstance> findAllByAreaIsNull();
    Mono<AgentInstance> findByArea_Nodes_IdContains(Integer nodeId);

}
