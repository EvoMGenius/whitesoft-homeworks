package com.evo.apatios.util.aspect;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.evo.apatios.aspect.logging.ApiRequestLoggingAspect;
import com.evo.apatios.controller.employee.logger.util.LoggingAppender;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ApiRequestLoggingAspectTest {


    private final MockHttpServletRequest request = new MockHttpServletRequest();

    private final JoinPoint joinPoint = mock(JoinPoint.class);
    private LoggingAppender logAppender;

    private final ApiRequestLoggingAspect loggingAspect = new ApiRequestLoggingAspect(request);

    @BeforeEach
    void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        logAppender = new LoggingAppender();
        logAppender.setContext(new LoggerContext());
        logAppender.start();

        Logger logger = (Logger) LoggerFactory.getLogger(ApiRequestLoggingAspect.class);
        logger.addAppender(logAppender);
    }

    @AfterEach
    void cleanUp() {
        logAppender.stop();
    }

    @Test
    void logRequest() {
        //arrange

        //act
        loggingAspect.logRequest(joinPoint);
        //assert
        assertApiRequestLog(null, null, "127.0.0.1", "", "");

    }

    private void assertApiRequestLog(String methodName, String params, String requestIdAddress, String endpoint, String operation) {
        String loggingExpected = String.format("method= %s, params= %s, request: ipAddress= %s, endpoint= %s, operation= %s", methodName, params, requestIdAddress, endpoint, operation);
        assertLog(loggingExpected);
    }

    private void assertLog(String expectedLog) {
        assertThat(logAppender.getEvents()).isNotEmpty().anySatisfy(event -> assertThat(event.getMessage()).isEqualTo(expectedLog));
    }
}
