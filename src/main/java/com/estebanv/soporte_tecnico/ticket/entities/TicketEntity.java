package com.estebanv.soporte_tecnico.ticket.entities;

import com.estebanv.soporte_tecnico.cliente.entities.ClienteEntity;
import com.estebanv.soporte_tecnico.tecnico.entities.TecnicoEntity;
import com.estebanv.soporte_tecnico.ticket.enums.EstadoEnum;
import com.estebanv.soporte_tecnico.ticket.enums.PrioridadEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_ticket", nullable = false, unique = true, length = 50)
    private String numeroTicket;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EstadoEnum estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PrioridadEnum prioridad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference
    private ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_asignado_id")
    @JsonBackReference
    private TecnicoEntity tecnicoAsignado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonBackReference
    private CategoriaEntity categoria;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("fechaCreacion ASC")
    private List<ComentarioEntity> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HistorialTicketEntity> historial = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "tiempo_resolucion_horas")
    private Integer tiempoResolucionHoras;


}
