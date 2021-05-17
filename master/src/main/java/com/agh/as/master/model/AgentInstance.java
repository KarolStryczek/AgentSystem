package com.agh.as.master.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentInstance {

    String id;
    Integer areaId;
    Integer instanceId;
    String instanceHost;
    List<Node> nodes;

}
