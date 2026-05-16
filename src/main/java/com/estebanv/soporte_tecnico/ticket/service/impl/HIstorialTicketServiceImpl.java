package com.estebanv.soporte_tecnico.ticket.service.impl;

import com.estebanv.soporte_tecnico.ticket.entities.HistorialTicketEntity;
import com.estebanv.soporte_tecnico.ticket.entities.TicketEntity;
import com.estebanv.soporte_tecnico.ticket.repositories.HistorialTicketJpaRepository;
import com.estebanv.soporte_tecnico.ticket.service.HistorialTicketCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class HIstorialTicketServiceImpl implements HistorialTicketCommandService {

    private final HistorialTicketJpaRepository historialTicketJpaRepository;

    @Override
    public void registrarHistorialTicker(TicketEntity ticketEntity, String campo,
                                         String valorAnterior, String valorNuevo) {
        HistorialTicketEntity historialTicketEntity = HistorialTicketEntity.builder()
                .ticket(ticketEntity)
                .campoModificado(campo)
                .valorAnterior(valorAnterior)
                .valorNuevo(valorNuevo)
                .usuarioModifico("tmpUser")
                .fechaModificacion(LocalDateTime.now())
                .build();

        historialTicketJpaRepository.save(historialTicketEntity);
    }


}
