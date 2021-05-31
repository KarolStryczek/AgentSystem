package com.agh.as.agent.utils;

import lombok.extern.slf4j.Slf4j;

import static net.logstash.logback.argument.StructuredArguments.f;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
public class LogUtils {

    public static void logSaveEntity(Object entity) {
        log.info("Save entity {}", f(entity));
    }
    public static void logFoundEntity(Object entity) {
        log.info("Found entity {}", f(entity));
    }
    public static void logDeleteEntity(Object entity) {
        log.info("Delete entity {}", f(entity));
    }
    public static void logUpdateEntity(Object entity) {
        log.info("Delete entity {}", f(entity));
    }
    public static void logGetRequestBody(String methodName, Object entity) {
        log.info("Get request body {} {}", kv("methodName", methodName), f(entity));
    }
    public static void logGetController(String methodName, String key, String value) {
        log.info("Get in {} {}", kv("methodName", methodName), kv(key, value));
    }
    public static void logGetResponseFrom(String serviceName, Object entity) {
        log.info("Get response {} {}", kv("serviceName", serviceName), f(entity));
    }
    public static void logSendSuccessfullyRequest(String serviceName, Object entity) {
        log.info("Send successfully to {}  request {}", kv("serviceName", serviceName), f(entity));
    }

}