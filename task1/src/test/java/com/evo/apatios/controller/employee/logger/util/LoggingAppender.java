package com.evo.apatios.controller.employee.logger.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public class LoggingAppender extends AppenderBase<ILoggingEvent> {

    public List<ILoggingEvent> getEvents() {
        return new ArrayList<>(events);
    }

    private final List<ILoggingEvent> events = new ArrayList<>();

    @Override
    protected void append(ILoggingEvent eventObject) {
        events.add(eventObject);
    }
}
