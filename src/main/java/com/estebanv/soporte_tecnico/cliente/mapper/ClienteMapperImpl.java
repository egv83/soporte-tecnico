package com.estebanv.soporte_tecnico.cliente.mapper;

import com.estebanv.soporte_tecnico.cliente.controller.response.ClienteResponse;
import com.estebanv.soporte_tecnico.cliente.entities.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteResponse toResponse(ClienteEntity entity) {
        return new ClienteResponse(
                entity.getId(),
                entity.getIdentificacion(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getEmail(),
                entity.getTelefono(),
                entity.getEmpresa(),
                entity.getIsActivo() ? "ACTIVE" : "INACTIVE",
                entity.getFechaRegistro()
        );
    }

}
