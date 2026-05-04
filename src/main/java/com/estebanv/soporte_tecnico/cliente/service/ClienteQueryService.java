package com.estebanv.soporte_tecnico.cliente.service;

import com.estebanv.soporte_tecnico.cliente.controller.response.ClienteResponse;
import com.estebanv.soporte_tecnico.cliente.entities.ClienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteQueryService {

    ClienteResponse getClienteById(Long id);

    List<ClienteResponse> getAllClientes();
    Page<ClienteEntity> getAllClientes(Pageable pageable);

}
