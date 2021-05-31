package com.agh.as.agent.dto.response;

import com.agh.as.agent.dto.NodeDto;
import com.agh.as.agent.model.Node;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Getter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteResponse {

    String id;
    Node current;
    Node target;
    List<NodeDto> currentRoute;
}
