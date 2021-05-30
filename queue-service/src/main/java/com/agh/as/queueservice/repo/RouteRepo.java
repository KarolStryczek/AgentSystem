package com.agh.as.queueservice.repo;

import com.agh.as.queueservice.model.Route;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RouteRepo extends ReactiveMongoRepository<Route, String> {

    Flux<Route> findByCurrentAgentOrderByCreatedAtAsc(Integer id);
    Flux<Route> findAllByCurrentAgentNotNull();
}
