package com.agh.as.agent.service;

import com.agh.as.agent.dto.request.AddAreaForm;
import com.agh.as.agent.dto.response.HeartBeatResponse;
import com.agh.as.agent.model.Area;
import com.agh.as.agent.model.Node;
import com.agh.as.agent.repo.AreaRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatusService {

    AreaRepo areaRepo;
    RoutsCache routsCache;

    public HeartBeatResponse getCurrentStatus() {
        Area currentArea = getArea();
        log.info("Get current status for agent [{}]", Objects.isNull(currentArea) ? "FREE" : currentArea.getId().toString());
        if (Objects.isNull(currentArea)) return new HeartBeatResponse("up");
        else return new HeartBeatResponse("up", currentArea.getId());
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
        List<Node> nodeInArea = addAreaForm.getNodes().stream().map(id -> map.get(id-1)).collect(Collectors.toList());
        Area test = new Area(addAreaForm.getId(), nodeInArea);
        Area savedArea = areaRepo.save(test);
        log.info("Set area for this agent to: [{}]", savedArea);
    }
}
