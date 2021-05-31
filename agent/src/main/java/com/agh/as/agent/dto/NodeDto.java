package com.agh.as.agent.dto;

import com.agh.as.agent.model.Node;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NodeDto {

    Integer id;
    Float x;
    Float y;
    List<BranchDto> branches;

    public NodeDto(Node node) {
        this.id = node.getId();
        this.x = node.getX();
        this.y = node.getY();
        this.branches = node.getBranches().stream().map(BranchDto::new).collect(Collectors.toList());
    }
}
