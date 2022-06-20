package com.evo.apatios.exception;

public class NotFoundPostException extends RuntimeException{

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public NotFoundPostException(){
        this.errorMessage = "POST IS NOT FOUND!";
    }
    public NotFoundPostException(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
