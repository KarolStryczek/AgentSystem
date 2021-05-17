package com.agh.as.agent.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Node {

    @Id
    Integer id;
    Float x;
    Float y;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "node_branch",
            joinColumns = @JoinColumn(name = "node_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    List<Branch> branches;

    public Node(String [] values) {
        this.id = Integer.valueOf(values[0]);
        this.x = Float.valueOf(values[1]);
        this.y = Float.valueOf(values[2]);
    }

    public Node(Integer id, Float x, Float y) {
        System.out.println("Node constriuct");
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void addBranch(Branch branch) {
        if (Objects.isNull(this.branches)) this.branches = new LinkedList<>();
        this.branches.add(branch);
    }
}
