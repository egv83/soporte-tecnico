package com.estebanv.soporte_tecnico.authentication.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @JsonProperty("username")
        @NotBlank(message = "El usuario es obligatorio")
        String userName,

        @NotBlank(message = "La clave es obligatorio")
        @Size(min = 5, message = "El minimo de caracteres es 5")
        String password
) {
}
