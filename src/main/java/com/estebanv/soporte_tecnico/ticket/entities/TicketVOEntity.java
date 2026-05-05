package com.estebanv.soporte_tecnico.ticket.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Immutable  // Importante: marca la entidad como inmutable (solo lectura)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "TicketVOEntity")
@Table(name = "vw_tickets_detalle")
public class TicketVOEntity {

    @Id
    private Long id;

    @Column(name = "numero_ticket")
    private String numeroTicket;

    private String titulo;

    @Column(name = "descripcion_resumida")
    private String descripcionResumida;

    private String estado;

    private String prioridad;

    // Datos del cliente
    @Column(name = "cliente_nombre")
    private String clienteNombre;

    @Column(name = "cliente_email")
    private String clienteEmail;

    @Column(name = "cliente_telefono")
    private String clienteTelefono;

    @Column(name = "cliente_empresa")
    private String clienteEmpresa;

    // Datos del técnico
    @Column(name = "tecnico_nombre")
    private String tecnicoNombre;

    @Column(name = "tecnico_telefono")
    private String tecnicoTelefono;

    // Datos de la categoría
    @Column(name = "categoria_nombre")
    private String categoriaNombre;

    @Column(name = "categoria_tiempo_estimado")
    private Integer categoriaTiempoEstimado;

    // Fechas formateadas (como String por el formato)
    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private String fechaActualizacion;

    @Column(name = "fecha_cierre")
    private String fechaCierre;

    // Tiempos calculados
    @Column(name = "tiempo_resolucion_horas")
    private Integer tiempoResolucionHoras;

    @Column(name = "horas_transcurridas")
    private Long horasTranscurridas;

    // Descripciones legibles
    @Column(name = "estado_descripcion")
    private String estadoDescripcion;

    @Column(name = "prioridad_visual")
    private String prioridadVisual;

    @Column(name = "estado_ticket")
    private String estadoTicket;

}
