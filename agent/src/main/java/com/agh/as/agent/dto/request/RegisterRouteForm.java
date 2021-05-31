package com.agh.as.agent.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRouteForm {

    String id;
    Integer startNode;
    Integer targetNode;
    Integer currentAgent;
}
