package com.evo.apatios.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> catchIllegalPostNameException(IllegalPostNameException e){
        log.error(e.getErrorMessage(), e);
        return new ResponseEntity<>(new MessageError(HttpStatus.BAD_REQUEST.value(), e.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> catchCreationEmployeeException(CreationEmployeeException e){
        log.error(e.getErrorMessage(), e);
        return new ResponseEntity<>(new MessageError(HttpStatus.BAD_REQUEST.value(), e.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }
}