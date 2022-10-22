package com.evo.bunkov.aspect.logging.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class LogUpdateDto {
    private UUID updatedEmployeeId;

    private Map<String, PairOfFields> updatedFields;
}
