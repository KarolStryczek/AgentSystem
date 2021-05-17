package com.agh.as.agent.controller;

import com.agh.as.agent.dto.request.UpdateRouteForm;
import com.agh.as.agent.service.RoutesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AgentController extends GenericController {

    RoutesService routesService;

    @PostMapping("/route/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGivenRoute(@RequestBody @Valid UpdateRouteForm updateRouteForm){
        routesService.updateRoute(updateRouteForm);
    }

}
