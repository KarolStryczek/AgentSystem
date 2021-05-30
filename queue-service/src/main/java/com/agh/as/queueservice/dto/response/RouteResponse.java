package com.agh.as.queueservice.dto.response;

import com.agh.as.queueservice.model.Node;
import com.agh.as.queueservice.model.Route;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteResponse {

    String id;
    Node current;
    Node target;

    public RouteResponse(Route route) {
        this.id = route.getId();
        this.current = route.getCurrent();
        this.target = route.getTarget();
    }
}
