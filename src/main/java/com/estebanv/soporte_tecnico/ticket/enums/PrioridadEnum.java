package com.estebanv.soporte_tecnico.ticket.enums;

import com.estebanv.soporte_tecnico.ticket.exception.TicketException;

import java.util.Arrays;

public enum PrioridadEnum {

    BAJA,
    MEDIA,
    ALTA,
    CRITICA;

    public static PrioridadEnum getPrioridadEnum(String prioridad) {
        try {
            return PrioridadEnum.valueOf(prioridad.toUpperCase());
        }catch (TicketException e){
            throw new  TicketException(
            String.format("Prioridad Invalida: '%s'. Valores permitidos: '%s'",
                    prioridad, Arrays.toString(prioridad.toCharArray()))
            );
        }
    }

}
