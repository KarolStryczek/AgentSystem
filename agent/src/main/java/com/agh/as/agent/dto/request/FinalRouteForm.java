package com.agh.as.agent.dto.request;

import com.agh.as.agent.dto.NodeDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinalRouteForm {

    String id;
    List<NodeDto> finalRoute;
}
