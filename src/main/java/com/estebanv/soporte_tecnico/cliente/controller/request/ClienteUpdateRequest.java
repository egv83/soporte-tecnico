package com.estebanv.soporte_tecnico.cliente.controller.request;

public record ClienteUpdateRequest(

        String nombre,
        String apellido,
        String email,
        String telefono,
        String empresa

) {
}
