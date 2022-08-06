package com.evo.bunkov.logger.dto;

import com.evo.bunkov.logger.model.PairOfFields;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class UpdateLogDto {
    private UUID id;

    private UUID updatedEmployeeId;

    private Map<String, PairOfFields> updatedFields;

    private LocalDateTime dateTime;
}
