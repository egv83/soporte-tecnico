package com.estebanv.soporte_tecnico.cliente.service;

import com.estebanv.soporte_tecnico.cliente.controller.request.ClienteCreateRequest;
import com.estebanv.soporte_tecnico.cliente.controller.request.ClienteUpdateRequest;
import com.estebanv.soporte_tecnico.cliente.controller.response.ClienteResponse;

import java.util.List;

public interface ClienteCommandService {

    ClienteResponse create(ClienteCreateRequest request);

    ClienteResponse update(ClienteUpdateRequest request, Long id);

    void delete(Long id);

}
