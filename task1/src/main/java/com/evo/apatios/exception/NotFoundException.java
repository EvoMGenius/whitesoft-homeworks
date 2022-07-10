package com.evo.apatios.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {

    private String errorMessage;
    private UUID id;

    public NotFoundException() {
        this.errorMessage = "Not Found";
    }

    public NotFoundException(String message) {
        this.errorMessage = message;
    }
}
