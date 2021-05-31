package com.agh.as.master.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Area {

    Integer areaId;
    List<Node> nodes;

    public Area(Integer areaId) {
        this.areaId = areaId;
    }

    public void addNode(Node node) {
        if (Objects.isNull(this.nodes)) this.nodes = new ArrayList<>();
        this.nodes.add(node);
    }
}
