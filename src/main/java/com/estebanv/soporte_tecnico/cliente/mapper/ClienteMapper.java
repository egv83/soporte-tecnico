package com.estebanv.soporte_tecnico.cliente.mapper;

import com.estebanv.soporte_tecnico.cliente.controller.response.ClienteResponse;
import com.estebanv.soporte_tecnico.cliente.entities.ClienteEntity;

public interface ClienteMapper {

    ClienteResponse toResponse(ClienteEntity entity);

}
