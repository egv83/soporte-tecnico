package com.estebanv.soporte_tecnico.exceptions;

import com.estebanv.soporte_tecnico.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.create(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return errorResponse.toString();
    }

    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccessError(DataAccessException ex) {
        log.error("Error de acceso a datos: {}", ex.getMessage());
        return ErrorResponse.create(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        ).toString();
    }

    @ExceptionHandler(JDBCConnectionException.class)
    public String handleDatabaseConectionError(DataAccessException ex) {
        log.error("Error de conexión a la base de datos: {}", ex.getMessage());
        return ErrorResponse.create(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        ).toString();
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception ex) {
        log.error("Error no controlado: {}", ex.getMessage(), ex);
        return ErrorResponse.create(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        ).toString();
    }

}
