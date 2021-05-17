package com.agh.as.agent.repo;

import com.agh.as.agent.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepo extends JpaRepository<Node, Integer> {
}
