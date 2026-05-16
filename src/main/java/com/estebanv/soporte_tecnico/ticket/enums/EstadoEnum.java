package com.estebanv.soporte_tecnico.ticket.enums;

import com.estebanv.soporte_tecnico.ticket.exception.TicketException;

import java.util.Arrays;

public enum EstadoEnum {

    ABIERTO,
    EN_PROCESO,
    EN_ESPERA,
    RESUELTO,
    CERRADO,
    CANCELADO;


    public static EstadoEnum getEstadoEnum(String estado) {
        try{
            return EstadoEnum.valueOf(estado.toUpperCase());
        }catch (TicketException e){
            throw new TicketException(
                    String.format("Estado invalido '%s'. Valores permitidos '%s",
                            estado, Arrays.toString(EstadoEnum.values())));
        }
    }

}
