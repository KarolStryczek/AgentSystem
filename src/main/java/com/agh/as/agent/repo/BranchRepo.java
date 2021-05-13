package com.agh.as.agent.repo;

import com.agh.as.agent.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepo extends JpaRepository<Branch, Integer> {
}
