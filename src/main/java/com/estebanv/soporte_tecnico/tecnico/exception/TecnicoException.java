package com.estebanv.soporte_tecnico.tecnico.exception;

public class TecnicoException extends RuntimeException {
    public TecnicoException(String message) {
        super(message);
    }

    public TecnicoException(String message, String data){
        super(String.format(message, data));
    }
}
