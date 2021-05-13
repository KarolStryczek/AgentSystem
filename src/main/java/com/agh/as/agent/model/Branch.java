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
public class Branch {

    @Id
    Integer id;
    Float cost;

    @ManyToMany(mappedBy = "branches", cascade = CascadeType.ALL)
    List<Node> nodes;

    public Branch(Integer id) {
        this.id = id;
    }
}
