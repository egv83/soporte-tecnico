package com.estebanv.soporte_tecnico.ticket.entities;

import com.estebanv.soporte_tecnico.cliente.entities.ClienteEntity;
import com.estebanv.soporte_tecnico.tecnico.entities.TecnicoEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Entity
@Table(name = "comentario")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ComentarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "es_interno")
    private Boolean esInterno = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketEntity ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    private TecnicoEntity tecnico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    // Validación: debe tener al menos un autor (técnico o cliente)
    @PrePersist
    @PreUpdate
    private void validarAutor() {
        if (tecnico == null && cliente == null) {
            throw new IllegalStateException("El comentario debe tener un técnico o un cliente como autor");
        }
    }

}
