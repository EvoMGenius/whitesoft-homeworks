package com.evo.apatios.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageError> catchNotFoundPostException(NotFoundPostException e){
        log.error(e.getErrorMessage(), e);
        return new ResponseEntity<>(new MessageError(e.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageError> catchCreationEmployeeException(CreationEmployeeException e){
        log.error(e.getErrorMessage(), e);
        return new ResponseEntity<>(new MessageError(e.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageError> catchCreationPostException(CreationPostException e){
        log.error(e.getErrorMessage(), e);
        return new ResponseEntity<>(new MessageError(e.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageError> catchNotFoundEmployeeException(NotFoundEmployeeException e){
        log.error(e.getErrorMessage(), e);
        return new ResponseEntity<>(new MessageError(e.getErrorMessage()), HttpStatus.NOT_FOUND);
    }
}