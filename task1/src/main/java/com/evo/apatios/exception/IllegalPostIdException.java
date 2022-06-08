package com.evo.apatios.exception;

public class IllegalPostIdException extends RuntimeException{

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public IllegalPostIdException(){
        this.errorMessage = "INCORRECT POST ID";
    }
    public IllegalPostIdException(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
