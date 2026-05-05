package com.estebanv.soporte_tecnico.ticket.service.impl;

import com.estebanv.soporte_tecnico.ticket.entities.TicketVOEntity;
import com.estebanv.soporte_tecnico.ticket.service.TicketQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketQueryService {
    @Override
    public Page<TicketVOEntity> busquedaAvanzada(String texto, String estado, String prioridad, String[] field, String order, int page, int size) {
        return null;
    }
}
