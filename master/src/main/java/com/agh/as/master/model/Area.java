package com.agh.as.master.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Area {

    Integer areaId;
    List<Node> nodes;

}
