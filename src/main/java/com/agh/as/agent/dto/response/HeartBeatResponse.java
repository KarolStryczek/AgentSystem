package com.agh.as.agent.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HeartBeatResponse {

    String status;
    Integer areaId;
    Integer instanceId;

    public HeartBeatResponse(String status, Integer instanceId) {
        this.status = status;
        this.instanceId = instanceId;
    }

    public HeartBeatResponse(String status, Integer areaId, Integer instanceId) {
        this.status = status;
        this.areaId = areaId;
        this.instanceId = instanceId;
    }
}
