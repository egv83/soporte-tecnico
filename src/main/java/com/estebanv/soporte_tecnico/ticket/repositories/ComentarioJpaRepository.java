package com.estebanv.soporte_tecnico.ticket.repositories;

import com.estebanv.soporte_tecnico.ticket.entities.ComentarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioJpaRepository extends JpaRepository<ComentarioEntity, Long> {

    Page<ComentarioEntity> findByTicketId(Long ticketId, Pageable pageable);

    // Ordenamiento específico
    List<ComentarioEntity> findByTicketIdOrderByFechaCreacionAsc(Long ticketId);

    // Últimos comentarios
    List<ComentarioEntity> findTop5ByTicketIdOrderByFechaCreacionDesc(Long ticketId);

}
