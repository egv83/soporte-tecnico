package com.estebanv.soporte_tecnico.authentication.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponse(
        @JsonProperty("username")
        String userName,
        String token
) {
}
