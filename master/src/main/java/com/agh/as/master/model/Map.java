package com.agh.as.master.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Map {

    List<Node> nodes;
    List<AgentInstance> agentInstances;


    public Map(List<Node> nodes) {
        this.nodes = nodes;
    }


}
