package com.agh.as.agent.dto.request;

import com.agh.as.agent.dto.NodeDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCreatingRouteForm {

    String id;
    List<NodeDto> newFragment;
    Integer newAgent;
}
