package com.agh.as.master.consumer;

import com.agh.as.master.dto.consumer.HeartBeatResponse;
import com.agh.as.master.utils.LogUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AgentConsumer {

    private final static String serviceName = "agent-service";

    public Mono<HeartBeatResponse> heartBeat(String host) {
        WebClient webClient = WebClient.builder().baseUrl(host).build();
        return webClient
                .get()
                .uri("/agent/status/beat")
                .retrieve()
                .bodyToMono(HeartBeatResponse.class)
                .doOnSuccess(response -> LogUtils.logGetResponseFrom(serviceName, response));
    }


}
