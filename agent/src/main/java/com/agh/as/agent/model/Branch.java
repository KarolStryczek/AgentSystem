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
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Setter
    Float cost;

    @ManyToMany(mappedBy = "branches", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Node> nodes;

    public Branch(String cost) {
        this.cost = Float.valueOf(cost);
    }
}
