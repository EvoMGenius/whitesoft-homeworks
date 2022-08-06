package com.evo.bunkov.logger.controller.mapper;

import com.evo.bunkov.logger.dto.RequestLogDto;
import com.evo.bunkov.logger.dto.UpdateLogDto;
import com.evo.bunkov.logger.dto.input.LogRequestDto;
import com.evo.bunkov.logger.dto.input.LogUpdateDto;
import com.evo.bunkov.logger.model.PairOfFields;
import com.evo.bunkov.logger.model.RequestLog;
import com.evo.bunkov.logger.model.UpdateLog;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    public UpdateLog updateDtoToEntity(LogUpdateDto logDto) {
        return UpdateLog.builder()
                        .updatedEmployeeId(logDto.getUpdatedEmployeeId())
                        .updatedFields(parse(
                                dtoToEntityPairOfFields(logDto.getUpdatedFields())))
                        .dateTime(LocalDateTime.now())
                        .build();

    }

    private Map<String, PairOfFields> dtoToEntityPairOfFields(Map<String, com.evo.bunkov.logger.dto.PairOfFields> updatedFields) {
        Map<String, PairOfFields> returningMap = new HashMap<>();
        updatedFields.forEach((s, pairOfFields) -> returningMap.put(s, new PairOfFields(pairOfFields.getLastValue(), pairOfFields.getNewValue())));
        return returningMap;
    }

    public UpdateLogDto updateEntityToDto(UpdateLog log) {
        return UpdateLogDto.builder()
                           .id(log.getId())
                           .updatedEmployeeId(log.getUpdatedEmployeeId())
                           .updatedFields(log.getUpdatedFields())
                           .dateTime(log.getDateTime())
                           .build();
    }

    private Map<String, PairOfFields> parse(Map<String, PairOfFields> map) {
        Map<String, PairOfFields> returningMap = new HashMap<>();

        map.forEach((s, pair) -> returningMap.put(s, new PairOfFields(pair.getLastValue(), pair.getNewValue())));

        return returningMap;
    }
}
