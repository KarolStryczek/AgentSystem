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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "node_branch",
            joinColumns = @JoinColumn(name = "node_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    List<Branch> branches;


    private static int idCounter = 0;
    @Transient
    public double f = Double.MAX_VALUE;
    @Transient
    public double g = Double.MAX_VALUE;
    @Transient
    public double h;
    @Transient
    public Node parent = null;


    public Node(String [] values) {
        this.id = Integer.valueOf(values[0]);
        this.x = Float.valueOf(values[1]);
        this.y = Float.valueOf(values[2]);
    }

    public Node(double h){
        this.h = h;
        this.id = idCounter++;
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
}
