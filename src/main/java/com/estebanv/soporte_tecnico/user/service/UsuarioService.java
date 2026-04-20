package com.estebanv.soporte_tecnico.user.service;

import com.estebanv.soporte_tecnico.user.controller.dto.request.UserUpdateRequest;
import com.estebanv.soporte_tecnico.user.entities.UsuarioEntity;

import java.util.List;

public interface UsuarioService {

    List<UsuarioEntity> getUsuarios();

    void create(UsuarioEntity usuarioEntity);

//    void update(UserUpdateRequest request, Long id);

    void updateUserPassword(UserUpdateRequest request, Long id);

}
