package com.agh.as.master.controller;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/agent")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentController {

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerInstance(){

    }


}
