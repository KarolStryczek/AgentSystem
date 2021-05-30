package com.agh.as.queueservice.dto.request;

import com.agh.as.queueservice.model.Node;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRouteForm {

    String id;
    List<Node> newFragment;
    Integer newAgent;

}
