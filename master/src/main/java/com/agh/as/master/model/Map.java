package com.agh.as.master.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Map {

    List<Node> nodes;
    List<Area> areas;

    public Map(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void addArea(Area area) {
        if (Objects.isNull(this.areas)) this.areas = new LinkedList<>();
        this.areas.add(area);
    }
}
