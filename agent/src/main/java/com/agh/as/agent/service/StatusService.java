package com.agh.as.agent.service;

import com.agh.as.agent.dto.request.AddAreaForm;
import com.agh.as.agent.dto.response.HeartBeatResponse;
import com.agh.as.agent.model.Area;
import com.agh.as.agent.model.Node;
import com.agh.as.agent.repo.AreaRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusService {

    @Value("${instance.id}")
    Integer instanceId;
    @Value("${instance.host}")
    String instanceHost;

    final AreaRepo areaRepo;
    final RoutsCache routsCache;

    public StatusService(AreaRepo areaRepo, RoutsCache routsCache) {
        this.areaRepo = areaRepo;
        this.routsCache = routsCache;
    }

    public HeartBeatResponse getCurrentStatus() {
        Area currentArea = getArea();
        log.info("Get current status for agent [{}]", Objects.isNull(currentArea) ? "FREE" : currentArea.getId().toString());
        if (Objects.isNull(currentArea)) return new HeartBeatResponse("up", instanceId, instanceHost);
        else return new HeartBeatResponse("up", currentArea.getId(), instanceId, instanceHost);
    }

    public Area getArea() {
        List<Area> all = areaRepo.findAll();
        if (all.isEmpty()) return null;
        return all.get(0);
    }

    public void setArea(AddAreaForm addAreaForm) {
        Area currentArea = getArea();
        if (!Objects.isNull(currentArea)) areaRepo.delete(currentArea);

        List<Node> map = routsCache.getMap("all");
        map.stream().filter(node -> addAreaForm.getNodes().contains(node.getId())).forEach(areaNode -> areaNode.setIsInThisAgent(true));
        Area test = new Area(addAreaForm.getId(), map);
        Area savedArea = areaRepo.save(test);
        log.info("Set area for this agent to: [{}]", savedArea);
    }
}
