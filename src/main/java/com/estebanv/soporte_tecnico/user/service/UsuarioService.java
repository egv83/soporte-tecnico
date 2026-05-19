package com.estebanv.soporte_tecnico.user.service;

import com.estebanv.soporte_tecnico.user.controller.dto.request.UserCreateRequest;
import com.estebanv.soporte_tecnico.user.controller.dto.request.UserUpdateRequest;
import com.estebanv.soporte_tecnico.user.controller.dto.response.AuthUserData;
import com.estebanv.soporte_tecnico.user.controller.dto.response.UserResponse;
import com.estebanv.soporte_tecnico.user.entities.UsuarioEntity;

import java.util.List;

public interface UsuarioService {

    List<UsuarioEntity> getUsuarios();

    UserResponse create(UserCreateRequest request);

//    void update(UserUpdateRequest request, Long id);

    void updateUserPassword(UserUpdateRequest request, Long id);

    AuthUserData validarCredenciales(String userName);


}
