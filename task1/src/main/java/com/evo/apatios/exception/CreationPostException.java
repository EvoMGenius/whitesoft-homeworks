package com.evo.apatios.exception;

public class CreationPostException extends RuntimeException{
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public CreationPostException (){
        this.errorMessage = "CREATION POST EXCEPTION";
    }
    public CreationPostException (String errorMessage){
        this.errorMessage = errorMessage;
    }
}
