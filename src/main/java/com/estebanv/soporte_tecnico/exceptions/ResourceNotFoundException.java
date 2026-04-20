package com.estebanv.soporte_tecnico.exceptions;

public class ResourceNotFoundException extends RuntimeException{


    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s no encontrado con id: %d", resourceName, id));
    }

}
