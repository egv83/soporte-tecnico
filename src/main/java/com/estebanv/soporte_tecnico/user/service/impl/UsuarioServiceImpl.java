package com.estebanv.soporte_tecnico.user.service.impl;

import com.estebanv.soporte_tecnico.common.security.PasswordEncoder;
import com.estebanv.soporte_tecnico.exceptions.ResourceNotFoundException;
import com.estebanv.soporte_tecnico.user.controller.dto.request.UserCreateRequest;
import com.estebanv.soporte_tecnico.user.controller.dto.request.UserUpdateRequest;
import com.estebanv.soporte_tecnico.user.controller.dto.response.AuthUserData;
import com.estebanv.soporte_tecnico.user.controller.dto.response.UserResponse;
import com.estebanv.soporte_tecnico.user.entities.UsuarioEntity;
import com.estebanv.soporte_tecnico.user.exceptions.UsuarioExceptions;
import com.estebanv.soporte_tecnico.user.mapper.UserMapper;
import com.estebanv.soporte_tecnico.user.repositories.UsuarioJpaRepository;
import com.estebanv.soporte_tecnico.user.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final PasswordEncoder passwordEncoder;

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final UserMapper userMapper;

    public UsuarioServiceImpl(PasswordEncoder passwordEncoder, UsuarioJpaRepository usuarioJpaRepository, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UsuarioEntity> getUsuarios() {
        return List.of();
    }

    @Override
    public UserResponse create(UserCreateRequest request) {
        log.info("Creando usuario {}", request.UserName());
        var userOpt = usuarioJpaRepository.findByUserName(request.UserName());
        if (userOpt.isPresent()) {
            throw new UsuarioExceptions("Ya existe el usuario: " + request.UserName());
        }

        if (request.password() == null || request.password().isEmpty()) {
            throw new UsuarioExceptions("Ingrese el password");
        }

        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .userName(request.UserName())
                .password(passwordEncoder.encode(request.password()))
                .isActived(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Usuario {} registrado", request.UserName());
        return userMapper.toDto(usuarioJpaRepository.save(usuarioEntity));

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
                        () -> new ResourceNotFoundException("Usuario", id)
                );

        usuarioJpaRepository.save(user);

    }

    @Override
    public AuthUserData validarCredenciales(String userName) {
        log.info("Validando credenciales de usuario...");

        return usuarioJpaRepository.findByUserName(userName)
                .map(userMapper::toAtuhDto)
                .orElseThrow(() -> new UsuarioExceptions("Usuario no encontrado: " + userName));


    }

}
