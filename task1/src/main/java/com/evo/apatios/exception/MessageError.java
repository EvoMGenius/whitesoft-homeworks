package com.evo.apatios.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class MessageError {
    private String message;
    private LocalDateTime timestamp;

    public MessageError(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
