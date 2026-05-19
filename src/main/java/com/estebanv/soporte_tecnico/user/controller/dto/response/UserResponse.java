package com.estebanv.soporte_tecnico.user.controller.dto.response;

import java.time.LocalDateTime;

public record UserResponse(
        String userName,
        String active,
        LocalDateTime createdAt

) {
}
