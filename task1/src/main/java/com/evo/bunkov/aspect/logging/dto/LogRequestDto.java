package com.evo.bunkov.aspect.logging.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogRequestDto {
    private String method;
    private String params;
    private String requestInfo;
}
