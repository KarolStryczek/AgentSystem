package com.agh.as.agent.service;

import com.agh.as.agent.consumer.MasterConsumer;
import com.agh.as.agent.consumer.QueueConsumer;
import com.agh.as.agent.dto.NodeDto;
import com.agh.as.agent.dto.request.FinalRouteForm;
import com.agh.as.agent.dto.request.RegisterRouteForm;
import com.agh.as.agent.dto.request.UpdateCreatingRouteForm;
import com.agh.as.agent.dto.request.UpdateRouteForm;
import com.agh.as.agent.dto.response.RouteResponse;
import com.agh.as.agent.model.Area;
import com.agh.as.agent.model.Node;
import com.agh.as.agent.repo.BranchRepo;
import com.agh.as.agent.repo.NodeRepo;
import com.agh.as.agent.utils.AStar;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        return processRoute(form.getId(), form.getStartNode(), form.getTargetNode(), new ArrayList<>());
    }


    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void updateNextRoute() {
        Area area = statusService.getArea();
        if (!Objects.isNull(area)){
            RouteResponse newRouteToCalculate = queueConsumer.getNewRouteToCalculate();
            if (!Objects.isNull(newRouteToCalculate) && StringUtils.hasLength(newRouteToCalculate.getId())) {
                processRoute(newRouteToCalculate.getId(), newRouteToCalculate.getCurrent().getId(), newRouteToCalculate.getTarget().getId(), newRouteToCalculate.getCurrentRoute());
            }
        }
    }

    private List<NodeDto> processRoute(String routeId, Integer startNode, Integer targetNode, List<NodeDto> currentRoute) {
        List<Node> nodes = statusService.getArea().getNodes();
        Node start = nodes.get(startNode-1);
        Node target = nodes.get(targetNode-1);
        List<Node> route = AStar.aStar(start, target);
        List<Node> routeInAgentArea = route.stream().filter(Node::getIsInThisAgent).collect(Collectors.toList());
        List<NodeDto> routeInAgentAreaDto = routeInAgentArea.stream().map(NodeDto::new).collect(Collectors.toList());
        Optional<Node> targetInRoute = routeInAgentArea.stream().filter(node -> node.getId().equals(target.getId())).findAny();
        UpdateCreatingRouteForm updateCreatingRouteForm = null;
        if (targetInRoute.isPresent()){
            List<NodeDto> finalRoute = new ArrayList<>(currentRoute);
            for (NodeDto node: routeInAgentAreaDto) {
                if (currentRoute.stream().noneMatch(n -> n.getId().equals(node.getId()))) {
                    finalRoute.add(node);
                }
            }
            FinalRouteForm finalRouteForm = new FinalRouteForm(routeId, finalRoute);
            updateCreatingRouteForm = new UpdateCreatingRouteForm(routeId, routeInAgentAreaDto, null);
            masterConsumer.setFinalRoute(finalRouteForm);
        } else {
            Node neighbourNode = routeInAgentArea.get(routeInAgentArea.size() - 1);
             updateCreatingRouteForm = new UpdateCreatingRouteForm(routeId, routeInAgentAreaDto, neighbourNode.getNeighborAgent());
        }
        queueConsumer.updateRoute(updateCreatingRouteForm);
        return routeInAgentAreaDto;
    }

}
