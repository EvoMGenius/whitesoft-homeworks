package com.evo.bunkov.logger.controller.mapper;

import com.evo.bunkov.logger.dto.RequestLogDto;
import com.evo.bunkov.logger.dto.input.LogRequestDto;
import com.evo.bunkov.logger.model.RequestLog;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogMapper {
    public RequestLog requestDtoToEntity(LogRequestDto logDto) {
        return RequestLog.builder()
                         .method(logDto.getMethod())
                         .params(logDto.getParams())
                         .requestInfo(logDto.getRequestInfo())
                         .dateTime(LocalDateTime.now())
                         .build();

    }

    public RequestLogDto requestEntityToDto(RequestLog log) {
        return RequestLogDto.builder()
                            .id(log.getId())
                            .method(log.getMethod())
                            .requestInfo(log.getRequestInfo())
                            .params(log.getParams())
                            .dateTime(log.getDateTime())
                            .build();
    }
}
