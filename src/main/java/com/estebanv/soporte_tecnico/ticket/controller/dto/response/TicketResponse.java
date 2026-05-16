package com.estebanv.soporte_tecnico.ticket.controller.dto.response;

import com.estebanv.soporte_tecnico.ticket.enums.EstadoEnum;
import com.estebanv.soporte_tecnico.ticket.enums.PrioridadEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TicketResponse(

        @JsonProperty("ticket")
        String numeroTicket,

        String titulo,

        EstadoEnum estado,

        PrioridadEnum prioridad,

        @JsonProperty("técnico asignado")
        String tecnicoAsignado,

        String categoria,

        @JsonProperty("fecha creación")
        LocalDateTime fechaCreacion,

        @JsonProperty("fecha actualización")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime fechaActualizacion,

        @JsonProperty("fecha cierre")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime fechaCierre,

        @JsonProperty("tiempo de solución")
        Integer tiempoSolucion


) {
}
