package com.estebanv.soporte_tecnico.ticket.mapper;

import com.estebanv.soporte_tecnico.tecnico.entities.TecnicoEntity;
import com.estebanv.soporte_tecnico.ticket.controller.dto.response.TicketResponse;
import com.estebanv.soporte_tecnico.ticket.entities.CategoriaEntity;
import com.estebanv.soporte_tecnico.ticket.entities.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    /*MAPPER DE TICKET RESPONSE*/
    @Mapping(source = "tecnicoAsignado", target = "tecnicoAsignado", qualifiedByName = "tecnicoToString")
    @Mapping(source = "categoria", target = "categoria", qualifiedByName = "categoriaToString")
    TicketResponse toResponse(TicketEntity entity);


    @Named("tecnicoToString")
    default String tecnicoToString(TecnicoEntity tecnico) {
        if (tecnico == null) {
            return null;
        }
        return tecnico.getNombre() + " " + tecnico.getApellido();
    }

    @Named("categoriaToString")
    default String categoriaToString(CategoriaEntity categoria) {
        if (categoria == null) {
            return null;
        }
        return categoria.getNombre();
    }

}
