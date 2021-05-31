package com.agh.as.agent.controller;

import com.agh.as.agent.dto.NodeDto;
import com.agh.as.agent.dto.request.RegisterRouteForm;
import com.agh.as.agent.dto.request.UpdateRouteForm;
import com.agh.as.agent.service.RoutesService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/${instance.id}")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentController extends GenericController {


    final RoutesService routesService;

    public AgentController(RoutesService routesService) {
        this.routesService = routesService;
    }

    @PostMapping("/route/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGivenRoute(@RequestBody @Valid UpdateRouteForm updateRouteForm){
        routesService.updateRoute(updateRouteForm);
    }

    @PostMapping("/route/start")
    public List<NodeDto> startCalculateRoute(@RequestBody RegisterRouteForm form){
        return routesService.startRoute(form);
    }

}
