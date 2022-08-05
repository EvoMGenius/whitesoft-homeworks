package com.evo.bunkov.logger.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestLog {

    @Id
    @GeneratedValue
    private UUID id;

    private String method;
    private String params;
    private String requestInfo;

    private LocalDateTime dateTime;

    public RequestLog(UUID id, String method, String params, String requestInfo) {
        this.id = id;
        this.method = method;
        this.params = params;
        this.requestInfo = requestInfo;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestLog)) return false;
        RequestLog log = (RequestLog) o;
        return id != null &&
               id.equals(log.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
