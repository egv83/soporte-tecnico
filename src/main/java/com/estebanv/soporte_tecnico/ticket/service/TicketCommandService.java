package com.estebanv.soporte_tecnico.ticket.service;

import com.estebanv.soporte_tecnico.ticket.controller.dto.request.TicketPatchRequest;
import com.estebanv.soporte_tecnico.ticket.controller.dto.request.TicketRequest;
import com.estebanv.soporte_tecnico.ticket.controller.dto.response.TicketResponse;

public interface TicketCommandService {

    TicketResponse crearTicket(TicketRequest ticketRequest);
    TicketResponse actualizarTicket(String ticket,TicketPatchRequest ticketRequest);

}
