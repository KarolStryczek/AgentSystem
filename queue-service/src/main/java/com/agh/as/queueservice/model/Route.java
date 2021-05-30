package com.agh.as.queueservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Document
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Route {

    @Id
    String id;
    Date createdAt;

    List<Node> currentRoute = new ArrayList<>();

    Node start;
    Node current;
    Node target;
    Integer currentAgent;
}
