package com.agh.as.master.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder(builderClassName = "RouteDataBuilder")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteData {


    @Id
    String id;

    Node start;
    Node target;

    List<Node> result;

    @JsonPOJOBuilder(withPrefix = "")
    public static class RouteDataBuilder {}
}
