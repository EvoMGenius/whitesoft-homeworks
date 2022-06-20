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
    public ResponseEntity<MessageError> catchIllegalPostNameException(NotFoundPostException e){
        log.error(e.getErrorMessage(), e);
        return new ResponseEntity<>(new MessageError(HttpStatus.BAD_REQUEST.value(), e.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageError> catchCreationEmployeeException(CreationEmployeeException e){
        log.error(e.getErrorMessage(), e);
        return new ResponseEntity<>(new MessageError(HttpStatus.BAD_REQUEST.value(), e.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageError> catchCreationPostException(CreationPostException e){
        log.error(e.getErrorMessage(), e);
        return new ResponseEntity<>(new MessageError(HttpStatus.BAD_REQUEST.value(), e.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageError> catchNotFoundEmployeeException(NotFoundEmployeeException e){
        log.error(e.getErrorMessage(), e);
        return new ResponseEntity<>(new MessageError(HttpStatus.NOT_FOUND.value(), e.getErrorMessage()), HttpStatus.NOT_FOUND);
    }
}