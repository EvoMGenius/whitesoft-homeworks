package com.evo.bunkov.aspect.logging.dto;

import com.mysema.commons.lang.Pair;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class LogUpdateDto {
    private UUID updatedEmployeeId;

    private Map<String , Pair<String, String >> updatedFields;
}
