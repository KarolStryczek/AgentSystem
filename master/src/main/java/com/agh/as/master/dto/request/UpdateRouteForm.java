package com.agh.as.master.dto.request;

import com.agh.as.master.model.Node;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRouteForm {

    String id;
    List<Node> finalRoute;
}
