package com.agh.as.agent.consumer;

import com.agh.as.agent.dto.request.RegisterInMasterForm;
import com.agh.as.agent.dto.response.RouteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class QueueConsumer extends RestTemplate {

    @Value("${instance.id}")
    Integer instanceId;
    @Value("${queue.host}")
    String queueHost;

    public void getNewRouteToCalculate() {
        String url = String.format("%s/queue/notify/agent/%s/id", queueHost, instanceId);
        try {
            getForEntity(url, RouteResponse.class);
        } catch (RestClientException e) {
            log.error("Error while connecting to queue with stack \n [{}]", (Object) e.getStackTrace());
        }
    }
}
