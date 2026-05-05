package com.estebanv.soporte_tecnico.ticket.repositories;

import com.estebanv.soporte_tecnico.ticket.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByClienteId(Long clienteId);
    Optional<TicketEntity> findByNumeroTicket(String numeroTicket);

}
