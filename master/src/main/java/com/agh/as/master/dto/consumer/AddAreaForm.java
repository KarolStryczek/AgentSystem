package com.agh.as.master.dto.consumer;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddAreaForm {

    Integer id;
    List<Integer> nodes;
    List<NeighborDto> neighbors;

}
