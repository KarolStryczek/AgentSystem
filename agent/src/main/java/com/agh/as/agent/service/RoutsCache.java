package com.agh.as.agent.service;

import com.agh.as.agent.model.Node;
import com.agh.as.agent.utils.AStar;
import com.agh.as.agent.utils.MapReader;
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
public class RoutsCache {

    @Cacheable("map")
    public List<Node> getMap(String startStop){
        if (startStop.equals("all"))
            return MapReader.readCSVMap();
        else
            return null;
    }
}
