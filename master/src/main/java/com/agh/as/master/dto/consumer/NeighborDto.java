package com.agh.as.master.dto.consumer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NeighborDto {

    Integer nodeId;
    Integer agentId;

}
