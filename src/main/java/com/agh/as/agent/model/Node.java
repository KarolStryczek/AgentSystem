package com.agh.as.agent.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

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

    public Node(Integer id) {
        this.id = id;
    }
}
