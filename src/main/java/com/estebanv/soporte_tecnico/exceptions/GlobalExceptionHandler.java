package com.estebanv.soporte_tecnico.exceptions;

import com.estebanv.soporte_tecnico.cliente.exception.ClienteException;
import com.estebanv.soporte_tecnico.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.create(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
        log.warn("Error en la validación de la petición");

        Map<String,String> fieldErrors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(
                        error-> fieldErrors.put(error.getField(),error.getDefaultMessage())
                );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.create(
                                fieldErrors.toString(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value()
                        )
                );
    }


    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessError(DataAccessException ex) {
      log.error("Error de acceso a datos: {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.create(
                                ex.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value()
                        )
                );
    }

    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseConectionError(DataAccessException ex) {
        log.error("Error de conexión a la base de datos: {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.create(
                                ex.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralError(Exception ex) {
        log.error("Error no controlado: {}",ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.create(
                                ex.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value()
                        )
                );
    }

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<ErrorResponse> handleClienteError(ClienteException ex) {
        log.error("Error en el módulo de clientes: {}",ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.create(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
