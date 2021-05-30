package com.agh.as.master.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Node {

    Integer id;
    Float x;
    Float y;

    public Node(String [] values) {
        this.id = Integer.valueOf(values[0]);
        this.x = Float.valueOf(values[1]);
        this.y = Float.valueOf(values[2]);
    }
}
