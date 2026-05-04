package com.estebanv.soporte_tecnico.tecnico.entities;//package com.estebanv.soporte_tecnico.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Boolean isActived;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

}
