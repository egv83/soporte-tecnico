package com.estebanv.soporte_tecnico.user.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(

        String userName,
        String password

) {
}
