package com.estebanv.soporte_tecnico.user.controller.dto.response;

public record AuthUserData(
        Long id,
        String userName,
        String password,
        boolean isActived,
        boolean isAccountNonLocked
) {
}
