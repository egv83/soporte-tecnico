package com.estebanv.soporte_tecnico.user.mapper;

import com.estebanv.soporte_tecnico.user.controller.dto.response.UserCreateResponse;
import com.estebanv.soporte_tecnico.user.entities.UsuarioEntity;

public class UserMapperImpl implements UserMapper{
    @Override
    public UserCreateResponse toDto(UsuarioEntity usuarioEntity) {
         return new UserCreateResponse(
                 usuarioEntity.getUserName(),
                 usuarioEntity.getIsActived() ? "ACTIVE" : "INACTIVE",
                 usuarioEntity.getCreatedAt()
         );
    }
}
