package com.agh.as.agent.dto.request;

import com.agh.as.agent.dto.NeighborDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddAreaForm {

    @NotNull(message = "Not null field")
    List<Integer> nodes;

    @NotNull(message = "Not null field")
    List<NeighborDto> neighbors;

    @NotNull(message = "Not null field")
    Integer id;

}
