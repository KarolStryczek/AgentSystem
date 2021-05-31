package com.agh.as.master.config;

import com.agh.as.master.consumer.AgentConsumer;
import com.agh.as.master.dto.consumer.HeartBeatResponse;
import com.agh.as.master.model.AgentInstance;
import com.agh.as.master.model.Map;
import com.agh.as.master.service.MapCache;
import com.agh.as.master.service.RunningAgentsService;
import com.agh.as.master.utils.AreaAllocator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppConfig {

    AgentConsumer agentConsumer;
    RunningAgentsService runningAgentsService;

    @PostConstruct
    public void setMapAndAllocateAreas() {
        runningAgentsService.deleteAll().subscribe();
    }

    @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() {
        runningAgentsService.getRunningAgentInstances()
                .flatMapSequential(this::updateStatus)
                .subscribe();
    }

    private Mono<HeartBeatResponse> updateStatus(AgentInstance instance) {
        return agentConsumer.heartBeat(instance.getInstanceHost())
                .doOnError(beat -> handleAgentInstanceFail(instance).subscribe());
    }

    private Mono<Void> handleAgentInstanceFail(AgentInstance instance) {
        if (Objects.isNull(instance.getArea()))
            return runningAgentsService.deleteFailedAgent(instance);
        else
            return runningAgentsService.deleteFailedAgentWithAssignedArea(instance);
    }



}
