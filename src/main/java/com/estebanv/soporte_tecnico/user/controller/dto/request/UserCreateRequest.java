package com.estebanv.soporte_tecnico.user.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(

        @NotBlank(message = "El usuario es obligatorio")
        String UserName,

        @NotBlank(message = "La clave es obligatorio")
        @Size(min = 5, message = "El minimo de caracteres es 5")
        String password
) {
}
