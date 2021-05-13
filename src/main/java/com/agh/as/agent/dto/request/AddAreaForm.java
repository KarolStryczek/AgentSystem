package com.agh.as.agent.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddAreaForm {

    @NotNull(message = "Not null field")
    List<Integer> nodes;

    @NotNull(message = "Not null field")
    Integer id;

}
