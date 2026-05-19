package com.estebanv.soporte_tecnico.authentication.controller.dto.response;

public record AuthUserData(
        Long id,
        String userName,
        String password,
        boolean isActived,
        boolean isAccountNonLocked
) {
}
