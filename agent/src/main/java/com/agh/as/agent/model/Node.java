package com.agh.as.agent.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Node implements Comparable<Node> {

    @Id
    Integer id;
    Float x;
    Float y;
    Boolean isInThisAgent;
    Integer neighborAgent;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "node_branch",
            joinColumns = @JoinColumn(name = "node_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    List<Branch> branches;

    public double h;


    @Transient
    public double f = Double.MAX_VALUE;
    @Transient
    public double g = Double.MAX_VALUE;
    @Transient
    public Node parent = null;


    public Node(String [] values) {
        this.id = Integer.valueOf(values[0]);
        this.x = Float.valueOf(values[1]);
        this.y = Float.valueOf(values[2]);
        this.h = 2;
        this.isInThisAgent = false;
    }


    public void addBranch(Branch branch) {
        if (Objects.isNull(this.branches)) this.branches = new LinkedList<>();
        this.branches.add(branch);
    }

    @Override
    public int compareTo(Node n) {
        return Double.compare(this.f, n.f);
    }

    public double calculateHeuristic(Node target){
        return this.h;
    }

    public boolean isOnBoarder() {
        return !Objects.isNull(this.neighborAgent);
    }
}
