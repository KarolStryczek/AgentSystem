package com.agh.as.queueservice.dto.response;

import com.agh.as.queueservice.model.Node;
import com.agh.as.queueservice.model.Route;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteResponse {

    String id;
    Node current;
    Node target;
    List<Node> currentRoute;

    public RouteResponse(Route route) {
        this.id = route.getId();
        this.current = route.getCurrent();
        this.target = route.getTarget();
        this.currentRoute = route.getCurrentRoute();
    }
}
