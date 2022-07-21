package com.evo.apatios.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreationEmployeeException extends RuntimeException {

    private String errorMessage;

    public CreationEmployeeException() {
        this.errorMessage = "CREATION EMPLOYEE EXCEPTION";
    }
}
