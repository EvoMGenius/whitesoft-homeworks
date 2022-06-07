package com.evo.apatios.exception;

public class CreationEmployeeException extends RuntimeException{
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public CreationEmployeeException (){
        this.errorMessage = "CREATION EMPLOYEE EXCEPTION";
    }
    public CreationEmployeeException (String errorMessage){
        this.errorMessage = errorMessage;
    }
}
