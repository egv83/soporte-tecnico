package com.estebanv.soporte_tecnico.config;

import com.estebanv.soporte_tecnico.dto.ErrorResponse;
import com.estebanv.soporte_tecnico.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, WebRequest request){
        ErrorResponse errorResponse = ErrorResponse.create(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                getPath(request)
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    private String getPath(WebRequest request){
        return request.getDescription(false).replace("usi=","");
    }

}
