package com.evo.bunkov.logger.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class RequestLogDto {
    private UUID id;
    private String method;
    private String params;
    private String requestInfo;
    private LocalDateTime dateTime;
}
