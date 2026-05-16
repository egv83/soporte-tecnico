package com.estebanv.soporte_tecnico.ticket.mapper;

import com.estebanv.soporte_tecnico.ticket.controller.dto.response.TicketVOResponse;
import com.estebanv.soporte_tecnico.ticket.entities.TicketVOEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketVOMapStruckts {

    TicketVOMapStruckts INSTANCE = Mappers.getMapper(TicketVOMapStruckts.class);

    /*MAPEO AUTOMATICO*/
    TicketVOResponse toResponse(TicketVOEntity entity);

    // Mapeo de listas
//    List<TicketVOResponse> toResponseList(List<TicketVOEntity> entities);
}
