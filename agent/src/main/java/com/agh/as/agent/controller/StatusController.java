package com.agh.as.agent.controller;

import com.agh.as.agent.dto.AreaDto;
import com.agh.as.agent.dto.request.AddAreaForm;
import com.agh.as.agent.dto.response.HeartBeatResponse;
import com.agh.as.agent.service.StatusService;
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
@RequestMapping("/status")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatusController {

    StatusService statusService;

    @GetMapping("/beat")
    public HeartBeatResponse heartBeat() {
        return statusService.getCurrentStatus();
    }

    @GetMapping("/area")
    public AreaDto getCurrentArea() {
        return new AreaDto(statusService.getArea());
    }

    @PostMapping("/area")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAreaToAgent(@Valid @RequestBody AddAreaForm addAreaForm) {
        statusService.setArea(addAreaForm);
    }
}
