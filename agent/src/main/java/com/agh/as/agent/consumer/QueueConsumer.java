package com.agh.as.agent.consumer;

import com.agh.as.agent.dto.request.RegisterInMasterForm;
import com.agh.as.agent.dto.request.RegisterRouteForm;
import com.agh.as.agent.dto.request.UpdateCreatingRouteForm;
import com.agh.as.agent.dto.response.RouteResponse;
import com.agh.as.agent.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class QueueConsumer extends RestTemplate {

    private static final String SERVICE_NAME = "queue-service";

    @Value("${instance.id}")
    Integer instanceId;
    @Value("${queue.host}")
    String queueHost;

    public RouteResponse getNewRouteToCalculate() {
        String url = String.format("%s/queue/notify/agent/%s/id", queueHost, instanceId);
        try {
            ResponseEntity<RouteResponse> response = getForEntity(url, RouteResponse.class);
            LogUtils.logGetResponseFrom(SERVICE_NAME, response.getBody());
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error while connecting to queue with stack \n [{}]", (Object) e.getStackTrace());
            return null;
        }
    }

    public void registerRoute(RegisterRouteForm form) {
        String url = String.format("%s/queue/route/register", queueHost);
        try {
            postForEntity(url, form, Void.class);
            LogUtils.logSendSuccessfullyRequest(SERVICE_NAME, form);
        } catch (RestClientException e) {
            log.error("Error while connecting to queue with stack \n [{}]", (Object) e.getStackTrace());
        }
    }

    public void updateRoute(UpdateCreatingRouteForm form) {
        String url = String.format("%s/queue/route/update", queueHost);
        try {
            postForEntity(url, form, Void.class);
            LogUtils.logSendSuccessfullyRequest(SERVICE_NAME, form);
        } catch (RestClientException e) {
            log.error("Error while connecting to queue with stack \n [{}]", (Object) e.getStackTrace());
        }
    }
}
