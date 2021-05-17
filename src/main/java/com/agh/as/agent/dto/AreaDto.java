package com.agh.as.agent.dto;

import com.agh.as.agent.model.Area;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AreaDto {

    Integer id;
    List<NodeDto> nodes;

    public AreaDto(Area area) {
        if (!Objects.isNull(area)) {
            this.id = area.getId();
            this.nodes = area.getNodes().stream().map(NodeDto::new).collect(Collectors.toList());
        }
    }
}
