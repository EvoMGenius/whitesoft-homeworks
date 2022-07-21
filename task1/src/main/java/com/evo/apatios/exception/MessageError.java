package com.evo.apatios.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageError {

    private String message;
    private LocalDateTime timestamp;

    public MessageError(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public MessageError(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
