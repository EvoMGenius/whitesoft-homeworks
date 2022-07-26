package com.evo.apatios.util.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
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
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        StringBuilder sb = new StringBuilder();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        sb.append("method= ")
          .append(getMethodName(joinPoint, signature))
          .append(", params= ")
          .append(getParams(joinPoint, method))
          .append(", request: ")
          .append(getRequestInfo(request));

        log.info(sb.toString());
    }

    private String getMethodName(ProceedingJoinPoint joinPoint, MethodSignature signature) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        return className + "." + methodName + "()";
    }

    private String getParams(ProceedingJoinPoint joinPoint, Method method) {
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
        return params.toString();
    }

    private String getRequestInfo(HttpServletRequest request) {
        return "ipAddress= " + request.getRemoteAddr() + ", "
               + "endpoint= " + request.getServletPath() + ", "
               + "requestTime= " + LocalDateTime.now() + ", "
               + "operation= " + request.getMethod();
    }

}
