package com.estebanv.soporte_tecnico.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp
) {

    public static ErrorResponse create(String message,  int status){
        return new ErrorResponse(status,message,LocalDateTime.now());
    }

}
