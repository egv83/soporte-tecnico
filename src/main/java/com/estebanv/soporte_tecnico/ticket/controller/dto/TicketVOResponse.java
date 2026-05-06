package com.estebanv.soporte_tecnico.ticket.controller.dto;


public record TicketVOResponse(

        Long id,
        String numeroTicket,
        String titulo,
        String descripcionResumida,
        String estado,
        String prioridad,

        // Datos del cliente
        String clienteNombre,
        String clienteEmail,
        String clienteTelefono,

        // Datos del técnico
        String tecnicoNombre,

        // Fechas formateadas (como String por el formato)
        String fechaCreacion,
        Long horasTranscurridas

) {
}
