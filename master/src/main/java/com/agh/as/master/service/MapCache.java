package com.agh.as.master.service;

import com.agh.as.master.model.Map;
import com.agh.as.master.model.Node;
import com.agh.as.master.utils.AStar;
import com.agh.as.master.utils.AreaAllocator;
import com.agh.as.master.utils.MapReader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MapCache {

    AreaAllocator areaAllocator;

    @Cacheable("map")
    public Map getMap(String startStop){
        if (startStop.equals("alloc")) {
            return areaAllocator.allocateAreas();
        } if (startStop.equals("all")) {
            return new Map(MapReader.readCSVMap());
        } else {
            return new Map(AStar.getRoute("a", "b"));
        }
    }
}
