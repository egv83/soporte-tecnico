package com.estebanv.soporte_tecnico.ticket.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Entity
@Table(name = "historial_ticket")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistorialTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    @JsonBackReference
    private TicketEntity ticket;

    @Column(name = "campo_modificado", nullable = false, length = 100)
    private String campoModificado;

    @Column(name = "valor_anterior", columnDefinition = "TEXT")
    private String valorAnterior;

    @Column(name = "valor_nuevo", columnDefinition = "TEXT")
    private String valorNuevo;

    @Column(name = "usuario_modifico", length = 200)
    private String usuarioModifico;

    @CreationTimestamp
    @Column(name = "fecha_modificacion", updatable = false)
    private LocalDateTime fechaModificacion;

}
