package com.agh.as.master.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Document
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentInstance {

    @Id
    String id;
    Integer instanceId;
    String instanceHost;

    @Setter
    Area area;


    public AgentInstance(Integer instanceId, String instanceHost) {
        this.instanceId = instanceId;
        this.instanceHost = instanceHost;
    }
}
