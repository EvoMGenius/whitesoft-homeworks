package com.evo.bunkov.logger.model;

import lombok.*;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateLog {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID updatedEmployeeId;

    @ElementCollection
    private Map<String, PairOfFields> updatedFields;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateLog)) return false;
        UpdateLog log = (UpdateLog) o;
        return id != null &&
               id.equals(log.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
