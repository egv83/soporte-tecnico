package com.estebanv.soporte_tecnico.ticket.service.impl;

import com.estebanv.soporte_tecnico.ticket.controller.dto.TicketVOResponse;
import com.estebanv.soporte_tecnico.ticket.entities.TicketVOEntity;
import com.estebanv.soporte_tecnico.ticket.mapper.TicketVOMapStruckts;
import com.estebanv.soporte_tecnico.ticket.repositories.TicketVORepository;
import com.estebanv.soporte_tecnico.ticket.service.TicketQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketQueryService {

    private final TicketVORepository ticketVORepository;
    private final TicketVOMapStruckts ticketVOMapStruckts;

    @Override
    public Page<TicketVOResponse> reporte(
            String texto, String estado, String prioridad, String[] field,
            String order, int page, int size
    ) {
        log.info("***** CONSULTA DE TICKETS AVANZADO *****");

        int pg = ((page >= 1) ? page - 1 : 0);
        Pageable pageable = PageRequest.of(
                pg, size,
                Sort.Direction.valueOf(order),
                field
        );

        Page<TicketVOEntity> entityPage = ticketVORepository.buscarAvanzada(
                texto, estado, prioridad, pageable
        );

        return entityPage.map(
                ticketVOMapStruckts::toResponse
        );
    }
}
