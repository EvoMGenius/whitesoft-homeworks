package com.evo.bunkov.logger.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Embeddable
public class PairOfFields {

    private String lastValue;

    private String newValue;
}
