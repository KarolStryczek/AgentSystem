package com.agh.as.agent.service;

import com.agh.as.agent.consumer.MasterConsumer;
import com.agh.as.agent.consumer.QueueConsumer;
import com.agh.as.agent.dto.NodeDto;
import com.agh.as.agent.dto.request.FinalRouteForm;
import com.agh.as.agent.dto.request.RegisterRouteForm;
import com.agh.as.agent.dto.request.UpdateCreatingRouteForm;
import com.agh.as.agent.dto.request.UpdateRouteForm;
import com.agh.as.agent.model.Node;
import com.agh.as.agent.repo.BranchRepo;
import com.agh.as.agent.repo.NodeRepo;
import com.agh.as.agent.utils.AStar;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoutesService {

    NodeRepo nodeRepo;
    StatusService statusService;
    MasterConsumer masterConsumer;
    QueueConsumer queueConsumer;
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

    public List<NodeDto> startRoute(RegisterRouteForm form) {
        queueConsumer.registerRoute(form);
        List<Node> nodes = statusService.getArea().getNodes();
        Node start = nodes.get(form.getStartNode()-1);
        Node target = nodes.get(form.getTargetNode()-1);
        List<Node> route = AStar.aStar(start, target);
        List<NodeDto> routeInAgentArea = route.stream().filter(Node::getIsInThisAgent).map(NodeDto::new).collect(Collectors.toList());
        Optional<NodeDto> targetInRoute = routeInAgentArea.stream().filter(node -> node.getId().equals(form.getTargetNode())).findAny();
        if (targetInRoute.isPresent()){
            FinalRouteForm finalRouteForm = new FinalRouteForm(form.getId(), routeInAgentArea);
            masterConsumer.setFinalRoute(finalRouteForm);
        } else {
            UpdateCreatingRouteForm updateCreatingRouteForm = new UpdateCreatingRouteForm(form.getId(), routeInAgentArea, 2);
            queueConsumer.updateRoute(updateCreatingRouteForm);
        }

        return routeInAgentArea;
    }


}
