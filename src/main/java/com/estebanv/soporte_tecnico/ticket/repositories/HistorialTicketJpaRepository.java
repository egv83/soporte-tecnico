package com.estebanv.soporte_tecnico.ticket.repositories;

import com.estebanv.soporte_tecnico.ticket.entities.HistorialTicketEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface HistorialTicketJpaRepository extends JpaRepository<HistorialTicketEntity, Long> {
}
