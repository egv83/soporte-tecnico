package com.estebanv.soporte_tecnico.authentication.controller.dto.response;

public record UserAuthentication(
        Long id,
        String username,
        String password,
        boolean isEnabled
) {
}
