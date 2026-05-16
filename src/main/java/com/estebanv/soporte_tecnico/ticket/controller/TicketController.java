package com.estebanv.soporte_tecnico.ticket.controller;

import com.estebanv.soporte_tecnico.ticket.controller.dto.request.TicketPatchRequest;
import com.estebanv.soporte_tecnico.ticket.controller.dto.request.TicketRequest;
import com.estebanv.soporte_tecnico.ticket.service.TicketCommandService;
import com.estebanv.soporte_tecnico.ticket.service.TicketQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketQueryService ticketQueryService;
    private final TicketCommandService ticketCommandService;

    @GetMapping("/reporte")
    public ResponseEntity<?> reporte(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String prioridad,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String[] field,
            @RequestParam(defaultValue = "ASC") String order
    ) {

        var tickets = ticketQueryService.reporte(texto, estado, prioridad, field, order, page, size);
        if (tickets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketRequest request) {
        return new ResponseEntity<>(
                ticketCommandService.crearTicket(request)
                , HttpStatus.CREATED
        );
    }

    @PatchMapping("/{ticket}")
    public ResponseEntity<?> updateTicket(
            @PathVariable(name = "ticket") String ticket,
            @Valid @RequestBody TicketPatchRequest request
    ) {
        return new ResponseEntity<>(
                ticketCommandService.actualizarTicket(ticket,request)
                ,HttpStatus.OK
        );
    }
}
