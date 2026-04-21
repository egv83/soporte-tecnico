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

import java.time.LocalDateTime;
import java.util.List;

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

        return clienteJpaRepository.findAll()
                .stream()
                .map(
                        clienteMapper::toResponse
                ).toList();
    }

    @Override
    public ClienteResponse create(ClienteCreateRequest request) {
        log.info("***** REGISTRI DE CLIENTE *****");
        var cliente = clienteJpaRepository.findByIdentificacion(request.identificacion());

        if(cliente.isPresent()){
            log.warn("El cliente ya existe con identificación: {}",request.identificacion());
            throw new ClienteException("El cliente ya existe con identificación: %s",request.identificacion());
        }

        return clienteMapper.toResponse(
                clienteJpaRepository.save(
                        ClienteEntity.builder()
                                .identificacion(request.identificacion())
                                .nombre(request.nombre())
                                .apellido(request.apellido())
                                .email(request.email())
                                .telefono(request.telefono())
                                .isActivo(true)
                                .fechaRegistro(LocalDateTime.now())
                                .build()
                )
        );
    }

    @Override
    public ClienteResponse update(ClienteUpdateRequest request, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
