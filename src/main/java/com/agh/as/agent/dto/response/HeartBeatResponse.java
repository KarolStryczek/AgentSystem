package com.agh.as.agent.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HeartBeatResponse {

    String status;
    Integer areaId;

    public HeartBeatResponse(String status) {
        this.status = status;
    }

    public HeartBeatResponse(String status, Integer areaId) {
        this.status = status;
        this.areaId = areaId;
    }
}
