package com.evo.apatios.exception;

public class IllegalPostNameException extends RuntimeException{

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public IllegalPostNameException(){
        this.errorMessage = "INCORRECT POST NAME";
    }
    public IllegalPostNameException(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
