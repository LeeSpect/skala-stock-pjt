package com.sk.skala.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class TimeAspect {

    @Around("execution(* com.sk.skala.stock.service.StockServiceImpl.simulateMarketDay(..))")
    public Object executionAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info("Aop로 잰 시간: {}", stopWatch.getTotalTimeMillis());

        return result;
    }
}
