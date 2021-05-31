package com.agh.as.agent.dto.response;

import com.agh.as.agent.model.Node;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteResponse {

    String id;
    Node current;
    Node target;
}
