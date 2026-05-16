package com.estebanv.soporte_tecnico.ticket.exception;

public class TicketException extends RuntimeException {
    public TicketException(String message) {
        super(message);
    }

    public TicketException(String message, String data){
        super(String.format(message, data));
    }
}
