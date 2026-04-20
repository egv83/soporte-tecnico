package com.estebanv.soporte_tecnico.user.service.impl;

import com.estebanv.soporte_tecnico.exceptions.ResourceNotFoundException;
import com.estebanv.soporte_tecnico.user.controller.dto.request.UserUpdateRequest;
import com.estebanv.soporte_tecnico.user.entities.UsuarioEntity;
import com.estebanv.soporte_tecnico.user.repositories.UsuarioJpaRepository;
import com.estebanv.soporte_tecnico.user.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioServiceImpl(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public List<UsuarioEntity> getUsuarios() {
        return List.of();
    }

    @Override
    public void create(UsuarioEntity usuarioEntity) {

    }

    @Override
    public void updateUserPassword(UserUpdateRequest request, Long id) {
        UsuarioEntity user = usuarioJpaRepository.findUsuarioById(id)
                .map(
                        usuarioEntity -> {
                            return UsuarioEntity.builder()
                                    .id(usuarioEntity.getId())
                                    .userName(
                                            request.userName() != null ? request.userName()
                                                    : usuarioEntity.getUserName()
                                    )
                                    .password(
                                            request.password() != null ? request.password()
                                                    : usuarioEntity.getPassword()
                                    )
                                    .build();
                        }
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException("Usuario",id)
                );

        usuarioJpaRepository.save(user);

    }

}
