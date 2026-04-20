package com.estebanv.soporte_tecnico.user.mapper;

import com.estebanv.soporte_tecnico.user.controller.dto.request.UserCreateRequest;
import com.estebanv.soporte_tecnico.user.controller.dto.response.UserCreateResponse;
import com.estebanv.soporte_tecnico.user.entities.UsuarioEntity;

public interface UserMapper {

    UserCreateResponse toDto(UsuarioEntity usuarioEntity);


}
