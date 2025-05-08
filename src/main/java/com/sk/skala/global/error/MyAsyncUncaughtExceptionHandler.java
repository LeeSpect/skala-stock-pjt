package com.sk.skala.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class MyAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {

        log.info("Excpetion message - {}", throwable.getMessage());
        log.info("Method - {}", method.getName());
        for (Object param : obj) {
            log.info("Parameter value - {}", param);
        }
    }
}
