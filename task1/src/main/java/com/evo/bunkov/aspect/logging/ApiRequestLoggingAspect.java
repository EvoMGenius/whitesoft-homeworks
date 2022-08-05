package com.evo.bunkov.aspect.logging;

import com.evo.bunkov.aspect.logging.dto.LogRequestDto;
import com.evo.bunkov.feing.LoggerServiceFeingClient;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
//@Slf4j
@ConditionalOnProperty(prefix = "logger", name = "controller")
@RequiredArgsConstructor
public class ApiRequestLoggingAspect {

    private final HttpServletRequest request;

    private final LoggerServiceFeingClient loggerFieng;

    @Pointcut("within(com.evo.bunkov.controller.*.*Controller)")
    public void controllerPointcut() {}

    @Before("controllerPointcut()")
    public void logRequest(JoinPoint point) {
        LogRequestDto logInfo = buildLoggerText(point);

        loggerFieng.logRequest(logInfo);
    }

    private LogRequestDto buildLoggerText(JoinPoint joinPoint) {
        return LogRequestDto.builder()
                            .method(joinPoint.getSignature().toString())
                            .params(Arrays.toString(joinPoint.getArgs()))
                            .requestInfo(getRequestInfo())
                            .build();
    }

    private String getRequestInfo() {
        return "ipAddress= " + request.getRemoteAddr() + ", "
               + "endpoint= " + request.getServletPath() + ", "
               + "operation= " + request.getMethod();
    }
}
