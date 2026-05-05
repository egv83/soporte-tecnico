package com.estebanv.soporte_tecnico.categoria.entities;//package com.estebanv.soporte_tecnico.entities;

import com.estebanv.soporte_tecnico.ticket.entities.TicketEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoria")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "tiempo_estimado_horas")
    private Integer tiempoEstimadoHoras;

    @Column(name = "is_activo")
    private Boolean isActivo;

    @CreationTimestamp /*PERMITE ASIGANAR LA FECHA Y HORA AL CREAR, NO EN ACTUALIZACIÓN*/
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "categoria")
    private List<TicketEntity> tickets = new ArrayList<>();

}
