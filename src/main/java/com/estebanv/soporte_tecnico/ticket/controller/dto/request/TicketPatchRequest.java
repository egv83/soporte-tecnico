package com.estebanv.soporte_tecnico.ticket.controller.dto.request;

public record TicketPatchRequest(
        String titulo,
        String descripcion,
        String estado,
        String prioridad,
        Long tecnicoId,
        Long categoriaId,
        String comentario,
        Boolean comentarioInterno
) {

}
