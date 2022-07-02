package com.evo.apatios.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {

    private String className;
    private String errorMessage;
    private UUID id;

    public NotFoundException() {
        this.errorMessage = "Not Found";
    }

    public NotFoundException(String message) {
        this.errorMessage = message;
    }


    public String getMessage() {
        StringBuilder sb = new StringBuilder("notFoundError{ ");
        if (className != null) sb.append("\"className\": \"").append(className).append("\", ");
        if (id != null) sb.append("\"id\": ").append(id).append(", ");
        sb.append("\"errorMessage\": \"").append(errorMessage).append("\" }");
        return sb.toString();
    }

}
