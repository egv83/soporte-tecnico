package com.estebanv.soporte_tecnico.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp
) {

    public static ErrorResponse create(String message,  int status){
        return new ErrorResponse(message,status,LocalDateTime.now());
    }

}
