package com.agh.as.master.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterAgentForm {

    @NotNull(message = "Not null field")
    Integer instanceId;
    @NotNull(message = "Not null field")
    String host;
}
