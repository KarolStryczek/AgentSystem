package com.agh.as.master.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HeartBeatResponse {

    String status;
    Integer areaId;
    Integer instanceId;
    String host;
}
