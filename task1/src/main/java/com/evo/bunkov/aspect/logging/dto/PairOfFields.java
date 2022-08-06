package com.evo.bunkov.aspect.logging.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PairOfFields {

    private String lastValue;

    private String newValue;

    public PairOfFields(String lastValue, String newValue) {
        this.lastValue = lastValue;
        this.newValue = newValue;
    }
}
