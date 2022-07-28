package com.evo.apatios.aspect.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(prefix = "logger", name = "controller")
public class ApiRequestLoggingAspect {

    @Pointcut("within(com.evo.apatios.controller.*.*Controller)")
    public void controllerPointcut() {}

    @Before("controllerPointcut()")
    public void logRequest(JoinPoint point) throws Throwable {
        saveLog(point);
    }


    void saveLog(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        sb.append("method= ")
          .append(joinPoint.getSignature())
          .append(", params= ")
          .append(Arrays.toString(joinPoint.getArgs()))
          .append(", request: ")
          .append(getRequestInfo(request));

        log.info(sb.toString());
    }

    String getRequestInfo(HttpServletRequest request) {
        return "ipAddress= " + request.getRemoteAddr() + ", "
               + "endpoint= " + request.getServletPath() + ", "
               + "requestTime= " + LocalDateTime.now() + ", "
               + "operation= " + request.getMethod();
    }

}
