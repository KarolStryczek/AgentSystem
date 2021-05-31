package com.agh.as.master.dto.consumer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRouteForm {

    @Setter
    String id;
    @Setter
    Integer currentAgent;

    Integer startNode;
    Integer targetNode;

    public RegisterRouteForm(Integer startNode, Integer targetNode) {
        this.startNode = startNode;
        this.targetNode = targetNode;
    }
}
