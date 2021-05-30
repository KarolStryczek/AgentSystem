package com.agh.as.master.utils;

import com.agh.as.master.model.Map;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AreaAllocator {

    @Value("${min.agent.num}")
    Integer minRunningAgents;

    public Map allocateAreas() {
        Map map = new Map(MapReader.readCSVMap());
        return map;
    }

}
