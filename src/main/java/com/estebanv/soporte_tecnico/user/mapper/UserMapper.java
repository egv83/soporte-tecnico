package com.estebanv.soporte_tecnico.user.mapper;

import com.estebanv.soporte_tecnico.user.controller.dto.response.AuthUserData;
import com.estebanv.soporte_tecnico.user.controller.dto.response.UserResponse;
import com.estebanv.soporte_tecnico.user.entities.UsuarioEntity;

public interface UserMapper {

    UserResponse toDto(UsuarioEntity usuarioEntity);

    AuthUserData toAtuhDto(UsuarioEntity usuarioEntity);


}
