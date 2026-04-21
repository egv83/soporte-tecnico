package com.estebanv.soporte_tecnico.cliente.controller.response;

import java.time.LocalDateTime;

public record ClienteResponse(

        Long id,
        String identificacion,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String empresa,
        String activo,
        LocalDateTime registrado

) {
}
