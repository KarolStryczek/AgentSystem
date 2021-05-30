package com.agh.as.master.repo;

import com.agh.as.master.model.RouteData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RouteRepo extends ReactiveMongoRepository<RouteData, String> {
}
