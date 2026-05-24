package com.estebanv.soporte_tecnico.tecnico.entities;//package com.estebanv.soporte_tecnico.entities;

import com.estebanv.soporte_tecnico.ticket.entities.ComentarioEntity;
import com.estebanv.soporte_tecnico.ticket.entities.TicketEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@Entity
@Table(name = "tecnico")
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "identificacion", nullable = false, length = 20)
    private String identificacion;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "is_activo")
    private Boolean isActivo;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "tecnicoAsignado")
    @JsonManagedReference
    private List<TicketEntity> ticketsAsignados = new ArrayList<>();

    @OneToMany(mappedBy = "tecnico")
    @JsonManagedReference
    private List<ComentarioEntity> comentarios = new ArrayList<>();

}
