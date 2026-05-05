package com.estebanv.soporte_tecnico.ticket.service;

import com.estebanv.soporte_tecnico.ticket.entities.TicketVOEntity;
import org.springframework.data.domain.Page;

public interface TicketQueryService {

    Page<TicketVOEntity> busquedaAvanzada(
            String texto, String estado, String prioridad,String[] field, String order,
            int page, int size
    );

}
