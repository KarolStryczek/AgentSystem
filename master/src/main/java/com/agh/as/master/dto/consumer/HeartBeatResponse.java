package com.agh.as.master.dto.consumer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HeartBeatResponse {

    String status;
    Integer areaId;
    Integer instanceId;
    String host;
}
