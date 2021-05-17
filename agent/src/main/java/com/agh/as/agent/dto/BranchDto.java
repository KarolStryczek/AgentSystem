package com.agh.as.agent.dto;

import com.agh.as.agent.model.Branch;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchDto {

    Integer id;
    Float cost;

    public BranchDto(Branch branch) {
        this.id = branch.getId();
        this.cost = branch.getCost();
    }
}
