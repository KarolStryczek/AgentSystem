package com.agh.as.agent.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HeartBeatResponse {

    String status;
    Integer areaId;
    Integer instanceId;
    String host;

    public HeartBeatResponse(String status, Integer instanceId, String host) {
        this.status = status;
        this.instanceId = instanceId;
        this.host = host;
    }

    public HeartBeatResponse(String status, Integer areaId, Integer instanceId, String host) {
        this.status = status;
        this.areaId = areaId;
        this.instanceId = instanceId;
        this.host = host;
    }
}
