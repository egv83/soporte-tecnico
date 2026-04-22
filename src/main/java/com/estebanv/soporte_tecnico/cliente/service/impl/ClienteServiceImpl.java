package com.estebanv.soporte_tecnico.cliente.service.impl;

import com.estebanv.soporte_tecnico.cliente.controller.request.ClienteCreateRequest;
import com.estebanv.soporte_tecnico.cliente.controller.request.ClienteUpdateRequest;
import com.estebanv.soporte_tecnico.cliente.controller.response.ClienteResponse;
import com.estebanv.soporte_tecnico.cliente.entities.ClienteEntity;
import com.estebanv.soporte_tecnico.cliente.exception.ClienteException;
import com.estebanv.soporte_tecnico.cliente.mapper.ClienteMapper;
import com.estebanv.soporte_tecnico.cliente.repositories.ClienteJpaRepository;
import com.estebanv.soporte_tecnico.cliente.service.ClienteCommandService;
import com.estebanv.soporte_tecnico.cliente.service.ClienteQueryService;
import com.estebanv.soporte_tecnico.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteQueryService, ClienteCommandService {

    private final ClienteJpaRepository clienteJpaRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public ClienteResponse getClienteById(Long id) {
        log.info("***** CONSULTA DE CLIENTE POR ID *****");

        return clienteJpaRepository.findById(id).
                map(
                        entity -> {
                            if (!entity.getIsActivo()) {
                                throw new ClienteException("El cliente esta inactivo");
                            }
                            return clienteMapper.toResponse(entity);
                        }
                )
                .orElseThrow(
                        () -> {
                            log.warn("Cliente no encontrado con id: {}", id);
                            return new ResourceNotFoundException("Cliente", id);
                        }
                );
    }

    @Override
    public List<ClienteResponse> getAllClientes() {
        log.info("***** CONSULTA DE CLIENTE POR ID *****");

        return clienteJpaRepository.findAllByIsActivo()
                .stream()
                .map(
                        clienteMapper::toResponse
                ).toList();
    }

    @Override
    public ClienteResponse create(ClienteCreateRequest request) {
        log.info("***** REGISTRI DE CLIENTE *****");
        var cliente = clienteJpaRepository.findByIdentificacion(request.identificacion());

        if (cliente.isPresent()) {
            log.warn("El cliente ya existe con identificación: {}", request.identificacion());
            throw new ClienteException("El cliente ya existe con identificación: %s", request.identificacion());
        }

        return clienteMapper.toResponse(
                clienteJpaRepository.save(
                        ClienteEntity.builder()
                                .identificacion(request.identificacion())
                                .nombre(request.nombre())
                                .apellido(request.apellido())
                                .email(request.email())
                                .telefono(request.telefono())
                                .empresa(request.empresa())
                                .isActivo(true)
                                .fechaRegistro(LocalDateTime.now())
                                .build()
                )
        );
    }

    @Override
    public void update(ClienteUpdateRequest request, Long id) {
        log.info("***** Actualización DE CLIENTE *****");

        log.info("***** ELIMINACION LOGICA DE CLIENTE *****");
        ClienteEntity cliente = clienteJpaRepository.findById(id)
                .orElseThrow(() -> {
                            log.warn("Cliente no encontrado con id: {}", id);
                            throw new ClienteException("Cliente no encontrado con id: %s", String.valueOf(id));
                        }
                );

        ClienteEntity clienteUpdate = cliente.toBuilder()
                .nombre(StringUtils.hasText(request.nombre()) ? request.nombre() :
                        cliente.getNombre())
                .apellido(StringUtils.hasText(request.apellido()) ? request.apellido() :
                        cliente.getApellido())
                .email(StringUtils.hasText(request.email()) ? request.email() :
                        cliente.getEmail())
                .telefono(StringUtils.hasText(request.telefono()) ? request.telefono() :
                        cliente.getTelefono())
                .empresa(StringUtils.hasText(request.empresa()) ? request.empresa() :
                        cliente.getEmpresa())
                .isActivo(Objects.nonNull(request.activo()) ? request.activo() :
                        cliente.getIsActivo())
                .build();

        System.out.println("CLIENTE UPDATE: " + clienteUpdate);

        clienteJpaRepository.save(clienteUpdate);

//        return null;
    }

    @Override
    public void delete(Long id) {

        log.info("***** ELIMINACION LOGICA DE CLIENTE *****");
        ClienteEntity cliente = clienteJpaRepository.findById(id)
                .orElseThrow(() -> {
                            log.warn("Cliente no encontrado con id: {}", id);
                            throw new ClienteException("Cliente no encontrado con id: %s", String.valueOf(id));
                        }
                );

        log.info("CLIENTE OBTENIDO: {}", cliente.getId());
        ClienteEntity clienteDisables = cliente.toBuilder()
                .isActivo(false)
                .build();

        log.info("CLIENTE ACTUALIZADO: {}", clienteDisables.getId());

        clienteJpaRepository.save(clienteDisables);

    }
}
