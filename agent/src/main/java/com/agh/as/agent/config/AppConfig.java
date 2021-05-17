package com.agh.as.agent.config;

import com.agh.as.agent.consumer.MasterConsumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppConfig {

    MasterConsumer masterConsumer;

    @PostConstruct
    public void registerPresence() {
        masterConsumer.registerPresence();
    }

}
