package com.agh.as.agent.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestController {


    @GetMapping("/test")
    public ResponseEntity testMessage() {
        return ResponseEntity.ok(Collections.singletonMap("status", "UP"));
    }

}
