package com.estebanv.soporte_tecnico.cliente.exception;

public class ClienteException extends RuntimeException {
    public ClienteException(String message) {
        super(message);
    }

    public ClienteException(String message, String data){
        super(String.format(message, data));
    }
}
