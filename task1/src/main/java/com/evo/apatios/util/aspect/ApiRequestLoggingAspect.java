package com.evo.apatios.util.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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

    @Around("controllerPointcut()")
    public Object logRequest(ProceedingJoinPoint point) throws Throwable {
        Object result;
        try {
            result = point.proceed();
            saveLog(point);
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(point.getArgs()),
                      point.getSignature().getDeclaringTypeName(), point.getSignature().getName());
            throw e;
        }
        return result;
    }


    private void saveLog(ProceedingJoinPoint joinPoint) {
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

    private String getRequestInfo(HttpServletRequest request) {
        return "ipAddress= " + request.getRemoteAddr() + ", "
               + "endpoint= " + request.getServletPath() + ", "
               + "requestTime= " + LocalDateTime.now() + ", "
               + "operation= " + request.getMethod();
    }

}
