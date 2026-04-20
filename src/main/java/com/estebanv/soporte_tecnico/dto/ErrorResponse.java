package com.estebanv.soporte_tecnico.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        String path
) {

    public static ErrorResponse create(String message,  int status, String path){
        return new ErrorResponse(message,status,LocalDateTime.now(), path);
    }

}
