package com.evo.bunkov.logger.dto.input;

import com.mysema.commons.lang.Pair;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class LogUpdateDto {
    private UUID updatedEmployeeId;

    private Map<String , Pair<String, String >> updatedFields;
}
