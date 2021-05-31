package com.agh.as.queueservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Node {

    Integer id;


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node))
            return false;
        Node node = (Node) obj;
        return (this.id.equals(node.getId()));
    }
}
