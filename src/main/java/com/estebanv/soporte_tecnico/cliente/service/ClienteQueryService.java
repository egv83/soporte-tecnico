package com.estebanv.soporte_tecnico.cliente.service;

import com.estebanv.soporte_tecnico.cliente.controller.response.ClienteResponse;

import java.util.List;

public interface ClienteQueryService {

    ClienteResponse getClienteById(Long id);

    List<ClienteResponse> getAllClientes();

}
