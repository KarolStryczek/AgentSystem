package com.agh.as.agent.dto;

import com.agh.as.agent.model.Node;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NodeDto {

    Integer id;
    List<BranchDto> branches;

    public NodeDto(Node node) {
        this.id = node.getId();
        this.branches = node.getBranches().stream().map(BranchDto::new).collect(Collectors.toList());
    }
}
