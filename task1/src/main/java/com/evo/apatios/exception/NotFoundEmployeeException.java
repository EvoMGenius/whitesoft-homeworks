package com.evo.apatios.exception;

public class NotFoundEmployeeException extends RuntimeException {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public NotFoundEmployeeException(){
        this.errorMessage = "EMPLOYEE IS NOT FOUND";
    }
    public NotFoundEmployeeException(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
