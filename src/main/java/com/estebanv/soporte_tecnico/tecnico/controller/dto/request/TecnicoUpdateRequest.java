package com.estebanv.soporte_tecnico.tecnico.controller.dto.request;

import jakarta.validation.constraints.Pattern;

public record TecnicoUpdateRequest(

        String nombre,
        String apellido,

        @Pattern(
                regexp = "^\\+?[0-9]{7,12}$",
                message = "El teléfono debe tener entre 7 a 12 dígitos, puede incluir + al inicio"
        )
        String telefono,
        Boolean activo
) {
}
