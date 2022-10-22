package com.evo.bunkov.logger.dto.input;

import com.evo.bunkov.logger.dto.PairOfFields;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class LogUpdateDto {
    private UUID updatedEmployeeId;

    private Map<String, PairOfFields> updatedFields;
}
