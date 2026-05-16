package com.estebanv.soporte_tecnico.ticket.service;

import com.estebanv.soporte_tecnico.ticket.entities.TicketEntity;

public interface HistorialTicketCommandService {

    void registrarHistorialTicker(TicketEntity ticketEntity, String campo,
                               String valorAnterior, String valorNuevo);
}
