package com.agh.as.agent.service;

import com.agh.as.agent.dto.request.UpdateRouteForm;
import com.agh.as.agent.model.Node;
import com.agh.as.agent.repo.BranchRepo;
import com.agh.as.agent.repo.NodeRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoutesService {

    NodeRepo nodeRepo;
    BranchRepo branchRepo;

    public void updateRoute(UpdateRouteForm updateRouteForm) {
        Optional<Node> sourceNode = nodeRepo.findById(updateRouteForm.getSource());
        sourceNode.flatMap(value -> value.getBranches().stream()
                .filter(branch -> branch.getNodes().stream().anyMatch(node -> node.getId().equals(updateRouteForm.getTarget())))
                .findFirst())
                .ifPresent(branch -> {
                    log.info("Found branch for update [{}-{}] for new cost [{}]", branch.getId(), branch.getCost(), updateRouteForm.getNewCost());
                    branch.setCost(updateRouteForm.getNewCost());
                    branchRepo.save(branch);
                });
    }
}
