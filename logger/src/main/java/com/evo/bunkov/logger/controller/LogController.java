package com.evo.bunkov.logger.controller;

import com.evo.bunkov.logger.controller.mapper.LogMapper;
import com.evo.bunkov.logger.dto.input.LogRequestDto;
import com.evo.bunkov.logger.dto.RequestLogDto;
import com.evo.bunkov.logger.service.RequestLogService;
import com.evo.bunkov.logger.service.UpdateLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logger")
public class LogController {

    private final RequestLogService requestLogService;

    private final UpdateLogService updateLogService;

    private final LogMapper LOG_MAPPER;

    @GetMapping
    public List<RequestLogDto> findAllRequestLogs() {
        return requestLogService.findAll().stream()
                                .map(LOG_MAPPER::requestEntityToDto)
                                .collect(Collectors.toList());
    }

    @PostMapping("/request")
    public void logRequest(@RequestBody LogRequestDto logDto) {
        requestLogService.save(LOG_MAPPER.requestDtoToEntity(logDto));
    }

}
