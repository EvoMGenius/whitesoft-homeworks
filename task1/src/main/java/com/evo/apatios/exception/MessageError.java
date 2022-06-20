package com.evo.apatios.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MessageError {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public MessageError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
