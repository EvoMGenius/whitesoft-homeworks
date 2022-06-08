package com.evo.apatios.exception;

public class UpdatingEmployeeException extends RuntimeException {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public UpdatingEmployeeException(){
        this.errorMessage = "UPDATING EMPLOYEE EXCEPTION";
    }
    public UpdatingEmployeeException(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
