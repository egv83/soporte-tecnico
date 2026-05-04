package com.estebanv.soporte_tecnico.tecnico.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TecnicoCreateRequest(
        @NotBlank(message = "Ingrese su documento de identificación")
        @NotNull(message = "Se requiere la identificación")
        String identificacion,

        @NotBlank(message = "Ingrese el nombre")
        String nombre,

        @NotBlank(message = "Ingrese el nombre")
        String apellido,

        @NotBlank(message = "Ingrese el telefono")
        @Pattern(
                regexp = "^\\+?[0-9]{7,12}$",
                message = "El teléfono debe tener entre 7 a 12 dígitos, puede incluir + al inicio"
        )
        String telefono
) {
}
