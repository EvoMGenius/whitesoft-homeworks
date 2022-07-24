package com.evo.apatios.util.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(prefix = "logger", name = "controller")
public class ControllersMethodLoggingAspect {

    @Pointcut("within(com.evo.apatios.controller.employee.EmployeeController) || within(com.evo.apatios.controller.post.PostController)")
    public void controllerPointcut() {}

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint jp, Throwable e) {
        log.error("Discovered  exception in {}.{} with message = {}",
                  jp.getSignature().getDeclaringTypeName(),
                  jp.getSignature().getName(),
                  e.getMessage());
    }

    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
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
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        logMethodName(joinPoint, signature);
        logParams(joinPoint, method);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        logRequest(request);
    }

    private void logMethodName(ProceedingJoinPoint joinPoint, MethodSignature signature) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        log.info("method= " + className + "." + methodName + "()");

    }

    private void logParams(ProceedingJoinPoint joinPoint, Method method) {
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = nameDiscoverer.getParameterNames(method);

        StringBuilder params = new StringBuilder();

        if (args != null && paramNames != null) {
            for (int i = 0; i < args.length; i++) {
                params.append("  ")
                      .append(paramNames[i])
                      .append(": ")
                      .append(args[i]);
            }
        }

        log.info("params=" + params);
    }

    private void logRequest(HttpServletRequest request) {
        log.info("ipAddress= " + request.getRemoteAddr());
        log.info("endpoint= " + request.getServletPath());
        log.info("requestTime= " + LocalDateTime.now());
        log.info("operation= " + request.getMethod());
    }

}
