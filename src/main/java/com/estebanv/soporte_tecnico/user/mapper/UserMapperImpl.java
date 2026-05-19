package com.estebanv.soporte_tecnico.user.mapper;

import com.estebanv.soporte_tecnico.user.controller.dto.response.AuthUserData;
import com.estebanv.soporte_tecnico.user.controller.dto.response.UserResponse;
import com.estebanv.soporte_tecnico.user.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper{
    @Override
    public UserResponse toDto(UsuarioEntity usuarioEntity) {
         return new UserResponse(
                 usuarioEntity.getUserName(),
                 usuarioEntity.getIsActived() ? "ACTIVE" : "INACTIVE",
                 usuarioEntity.getCreatedAt()
         );
    }

    @Override
    public AuthUserData toAtuhDto(UsuarioEntity usuarioEntity) {
        return new AuthUserData(
                usuarioEntity.getId(),
                usuarioEntity.getUserName(),
                usuarioEntity.getPassword(),
                usuarioEntity.getIsActived(),
                usuarioEntity.getIsAccountNonLocked()
        );
    }
}
