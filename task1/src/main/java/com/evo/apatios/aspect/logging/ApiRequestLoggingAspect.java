package com.evo.apatios.aspect.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(prefix = "logger", name = "controller")
@RequiredArgsConstructor
public class ApiRequestLoggingAspect {

    private final HttpServletRequest request;

    @Pointcut("within(com.evo.apatios.controller.*.*Controller)")
    public void controllerPointcut() {}

    @Before("controllerPointcut()")
    public void logRequest(JoinPoint point) {
        String logInfo = buildLoggerText(point);

        log.info(logInfo);
    }

    private String buildLoggerText(JoinPoint joinPoint) {
        return "method= " +
               joinPoint.getSignature() +
               ", params= " +
               Arrays.toString(joinPoint.getArgs()) +
               ", request: " +
               getRequestInfo();
    }

    private String getRequestInfo() {
        return "ipAddress= " + request.getRemoteAddr() + ", "
               + "endpoint= " + request.getServletPath() + ", "
               + "operation= " + request.getMethod();
    }
}
