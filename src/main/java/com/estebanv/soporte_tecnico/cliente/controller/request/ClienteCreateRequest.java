package com.estebanv.soporte_tecnico.cliente.controller.request;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.NumberFormat;

public record ClienteCreateRequest(

        @NotBlank(message = "Ingrese su documento de identificación")
        @NotNull(message = "Se requiere la identificación")
        String identificacion,

        @NotBlank(message = "Ingrese el nombre")
        String nombre,

        @NotBlank(message = "Ingrese el apellido")
        String apellido,

        @NotBlank(message = "Ingrese el email")
        @Email(message = "Ingrese un email valido")
        String email,

        @NotBlank(message = "Ingrese el telefono")
        @Pattern(
                regexp = "^\\+?[0-9]{7,12}$",
                message = "El teléfono debe tener entre 7 a 12 dígitos, puede incluir + al inicio"
        )
        String telefono,

        String empresa
) {
}
