package com.evo.bunkov.logger.dto.input;

import lombok.Data;

@Data
public class LogRequestDto {
    private String method;
    private String params;
    private String requestInfo;
}
