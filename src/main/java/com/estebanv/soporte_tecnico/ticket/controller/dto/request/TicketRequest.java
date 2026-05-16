package com.estebanv.soporte_tecnico.ticket.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TicketRequest(

        @NotBlank(message = "Ingrese un titulo")
        @NotNull(message = "El titulo es requerido")
        String titulo,

        @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
        String descripcion,

        @NotNull(message = "Se requiere la prioridad")
        String prioridad,

        @NotNull(message = "Se requiere un cliente")
        @Positive(message = "El ID del cliente debe ser positivo")
        Long clienteId,

        @NotNull(message = "Se requiere una categoria")
        @Positive(message = "El ID de la categoría debe ser positivo")
        Long categoriaId,

        @Size(max = 500, message = "El comentario no puede exceder 500 caracteres")
        String comentario
) {
}
