package com.agh.as.master.config;

import com.agh.as.master.model.Map;
import com.agh.as.master.service.MapCache;
import com.agh.as.master.utils.AreaAllocator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppConfig {

    MapCache mapCache;

    @PostConstruct
    public void setMapAndAllocateAreas() {
        mapCache.getMap("all");
    }

}
