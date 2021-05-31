package com.agh.as.agent.consumer;

import com.agh.as.agent.dto.request.FinalRouteForm;
import com.agh.as.agent.dto.request.RegisterInMasterForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class MasterConsumer extends RestTemplate {

    @Value("${instance.id}")
    Integer instanceId;
    @Value("${instance.host}")
    String instanceHost;
    @Value("${master.host}")
    String masterHost;


    public void registerPresence() {
        String url = String.format("%s/agent/register", masterHost);
        RegisterInMasterForm register = new RegisterInMasterForm(instanceId, instanceHost);
        try {
            postForEntity(url, register, Void.class);
        } catch (RestClientException e) {
            log.error("Error while connecting to master with stack \n [{}]", (Object) e.getStackTrace());
        }
    }

    public void setFinalRoute(FinalRouteForm form) {
        String url = String.format("%s/agent/route/finish", masterHost);
        try {
            postForEntity(url, form, Void.class);
        } catch (RestClientException e) {
            log.error("Error while connecting to master with stack \n [{}]", (Object) e.getStackTrace());
        }
    }

}
