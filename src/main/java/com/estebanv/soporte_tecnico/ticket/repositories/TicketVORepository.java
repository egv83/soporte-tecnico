package com.estebanv.soporte_tecnico.ticket.repositories;

import com.estebanv.soporte_tecnico.ticket.entities.TicketVOEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketVORepository extends JpaRepository<TicketVOEntity, Long> {

    @Query("SELECT v FROM TicketVOEntity v WHERE " +
            "(:texto IS NULL OR " +
            "   LOWER(v.titulo) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
            "   LOWER(v.clienteNombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
            "   LOWER(v.numeroTicket) LIKE LOWER(CONCAT('%', :texto, '%'))) " +
            "AND (:estado IS NULL OR v.estado = :estado) " +
            "AND (:prioridad IS NULL OR v.prioridad = :prioridad)")
    Page<TicketVOEntity> buscarAvanzada(@Param("texto") String texto, @Param("estado") String estado,
                                        @Param("prioridad") String prioridad, Pageable pageable);

}
