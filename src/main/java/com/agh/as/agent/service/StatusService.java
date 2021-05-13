package com.agh.as.agent.service;

import com.agh.as.agent.dto.request.AddAreaForm;
import com.agh.as.agent.dto.response.HeartBeatResponse;
import com.agh.as.agent.model.Area;
import com.agh.as.agent.model.Branch;
import com.agh.as.agent.model.Node;
import com.agh.as.agent.repo.AreaRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatusService {

    AreaRepo areaRepo;

    public HeartBeatResponse getCurrentStatus() {
        Area currentArea = getArea();
        if (Objects.isNull(currentArea)) return new HeartBeatResponse("up");
        else return new HeartBeatResponse("up", currentArea.getId());
    }

    public void setArea(AddAreaForm addAreaForm) {
        Area currentArea = getArea();
        if (!Objects.isNull(currentArea)) areaRepo.delete(currentArea);

        Area test = new Area(2, Arrays.asList(new Node(1, 1.2f, 1.3f, Arrays.asList(new Branch(1), new Branch(2))), new Node(3)));
        areaRepo.save(test);
    }

    public Area getArea() {
        List<Area> all = areaRepo.findAll();
        if (all.isEmpty()) return null;
        return all.get(0);
    }


}
