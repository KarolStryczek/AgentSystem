package com.agh.as.master.consumer;

import com.agh.as.master.dto.consumer.AddAreaForm;
import com.agh.as.master.dto.consumer.HeartBeatResponse;
import com.agh.as.master.dto.consumer.RegisterRouteForm;
import com.agh.as.master.dto.request.UpdateAgentRouteForm;
import com.agh.as.master.utils.LogUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
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

    public Mono<Void> startRoute(String host, String instanceId, RegisterRouteForm registerRouteForm) {
        WebClient webClient = WebClient.builder().baseUrl(host).build();
        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/agent/api/{instanceId}/route/start").build(instanceId))
                .body(BodyInserters.fromValue(registerRouteForm))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(ignored -> LogUtils.logSendSuccessfullyRequest(serviceName, registerRouteForm));
    }

    public Mono<Void> updateRoute(String host, String instanceId, UpdateAgentRouteForm updateAgentRouteForm) {
        WebClient webClient = WebClient.builder().baseUrl(host).build();
        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/agent/api/{instanceId}/route/update").build(instanceId))
                .body(BodyInserters.fromValue(updateAgentRouteForm))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(ignored -> LogUtils.logSendSuccessfullyRequest(serviceName, updateAgentRouteForm));
    }

    public Mono<Void> setArea(String host, AddAreaForm addAreaForm) {
        WebClient webClient = WebClient.builder().baseUrl(host).build();
        return webClient
                .post()
                .uri("/agent/status/area")
                .body(BodyInserters.fromValue(addAreaForm))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(ignored -> LogUtils.logSendSuccessfullyRequest(serviceName, addAreaForm));
    }


}
