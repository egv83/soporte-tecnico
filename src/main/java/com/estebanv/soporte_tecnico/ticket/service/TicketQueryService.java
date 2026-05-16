package com.estebanv.soporte_tecnico.ticket.service;

import com.estebanv.soporte_tecnico.ticket.controller.dto.response.TicketVOResponse;
import org.springframework.data.domain.Page;

public interface TicketQueryService {

    Page<TicketVOResponse> reporte(
            String texto, String estado, String prioridad,String[] field, String order,
            int page, int size
    );

}
