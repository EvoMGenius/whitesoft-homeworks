package com.evo.apatios.util.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.Mockito.*;

class ApiRequestLoggingAspectTest {

    private final MockHttpServletRequest request = new MockHttpServletRequest();

    private final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

    private final ApiRequestLoggingAspect loggingAspect = new ApiRequestLoggingAspect();

    @BeforeEach
    void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void logRequest() throws Throwable {
        // Arrange
        Object expectedResult = "expected result";

        when(joinPoint.proceed()).thenReturn(expectedResult);
        // Act
        Object result = loggingAspect.logRequest(joinPoint);
        // Assert
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void logRequestThrowsException() throws Throwable {
        // Arrange
        Throwable e = new Throwable();
        when(joinPoint.proceed()).thenThrow(e);
        // Act & Assert
        Assertions.assertThrows
                          (e.getClass(), () -> loggingAspect.logRequest(joinPoint));
    }

}