package com.evo.apatios.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreationPostException extends RuntimeException {

    private String errorMessage;

    public CreationPostException() {
        this.errorMessage = "CREATION POST EXCEPTION";
    }
}
