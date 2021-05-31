package com.agh.as.master.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAgentRouteForm {

    @NotNull(message = "Not null field")
    Integer source;
    @NotNull(message = "Not null field")
    Integer target;
    @NotNull(message = "Not null field")
    Float newCost;
}
