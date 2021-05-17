package com.agh.as.master.model;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentInstance {

    String id;
    Integer instanceId;
    String instanceHost;

    @Setter
    Area area;


    public AgentInstance(Integer instanceId, String instanceHost) {
        this.instanceId = instanceId;
        this.instanceHost = instanceHost;
    }
}
