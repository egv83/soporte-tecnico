package com.estebanv.soporte_tecnico.ticket.service.impl;

import com.estebanv.soporte_tecnico.ticket.repositories.CategoriaJpaRepository;
import com.estebanv.soporte_tecnico.cliente.repositories.ClienteJpaRepository;
import com.estebanv.soporte_tecnico.tecnico.entities.TecnicoEntity;
import com.estebanv.soporte_tecnico.tecnico.repositories.TecnicoJpaRepository;
import com.estebanv.soporte_tecnico.ticket.controller.dto.request.TicketPatchRequest;
import com.estebanv.soporte_tecnico.ticket.controller.dto.request.TicketRequest;
import com.estebanv.soporte_tecnico.ticket.controller.dto.response.TicketResponse;
import com.estebanv.soporte_tecnico.ticket.controller.dto.response.TicketVOResponse;
import com.estebanv.soporte_tecnico.ticket.entities.ComentarioEntity;
import com.estebanv.soporte_tecnico.ticket.entities.TicketEntity;
import com.estebanv.soporte_tecnico.ticket.entities.TicketVOEntity;
import com.estebanv.soporte_tecnico.ticket.enums.EstadoEnum;
import com.estebanv.soporte_tecnico.ticket.enums.PrioridadEnum;
import com.estebanv.soporte_tecnico.ticket.exception.TicketException;
import com.estebanv.soporte_tecnico.ticket.mapper.TicketMapper;
import com.estebanv.soporte_tecnico.ticket.mapper.TicketVOMapStruckts;
import com.estebanv.soporte_tecnico.ticket.repositories.ComentarioJpaRepository;
import com.estebanv.soporte_tecnico.ticket.repositories.TicketJpaRepository;
import com.estebanv.soporte_tecnico.ticket.repositories.TicketVORepository;
import com.estebanv.soporte_tecnico.ticket.service.HistorialTicketCommandService;
import com.estebanv.soporte_tecnico.ticket.service.TicketCommandService;
import com.estebanv.soporte_tecnico.ticket.service.TicketQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketQueryService, TicketCommandService {

    private final TicketVORepository ticketVORepository;
    private final CategoriaJpaRepository categoriaJpaRepository;
    private final HistorialTicketCommandService historialTicketCommandService;

    /*JPA*/
    private final ClienteJpaRepository clienteJpaRepository;
    private final TicketJpaRepository ticketJpaRepository;
    private final ComentarioJpaRepository comentarioJpaRepository;
    private final TecnicoJpaRepository tecnicoJpaRepository;


    /*MAPPERS*/
    private final TicketVOMapStruckts ticketVOMapStruckts;
    private final TicketMapper ticketMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<TicketVOResponse> reporte(
            String texto, String estado, String prioridad, String[] field,
            String order, int page, int size
    ) {
        log.info("***** CONSULTA DE TICKETS AVANZADO *****");

        int pg = ((page >= 1) ? page - 1 : 0);
        Pageable pageable = PageRequest.of(
                pg, size,
                Sort.Direction.valueOf(order),
                field
        );

        Page<TicketVOEntity> entityPage = ticketVORepository.buscarAvanzada(
                texto, estado, prioridad, pageable
        );

        return entityPage.map(
                ticketVOMapStruckts::toResponse
        );
    }

    @Transactional
    @Override
    public TicketResponse crearTicket(TicketRequest request) {

        log.info("***** CREANDO TICKET *****");

        /*BUSCAR CLIENTE*/
        var cliente = clienteJpaRepository.findById(request.clienteId())
                .orElseThrow(
                        () -> {
                            log.warn(
                                    "Cliente no encontrado con id: {}",
                                    request.clienteId()
                            );
                            return new TicketException(
                                    "El Cliente no existe"
                                    , request.clienteId().toString()
                            );
                        }
                );

        if (!cliente.getIsActivo()) {
            throw new TicketException("El cliente esta inactivo", request.clienteId().toString());
        }

        /*BUSACAR CATEGORIA*/
        var categoria = categoriaJpaRepository.findById(request.categoriaId())
                .orElseThrow(
                        () -> new TicketException("La categoría no existe: ", request.categoriaId().toString())
                );

        /*CREAR EL TICKET*/

        PrioridadEnum nuevaPrioridad = PrioridadEnum.getPrioridadEnum(request.prioridad());

        TicketEntity ticketEntity = TicketEntity.builder()
                .numeroTicket(generateTicketNumber())
                .titulo(request.titulo())
                .descripcion(request.descripcion())
                .estado(EstadoEnum.ABIERTO)
                .prioridad(request.prioridad() != null ? nuevaPrioridad : PrioridadEnum.BAJA)
                .cliente(cliente)
                .categoria(categoria)
                .build();

        TicketEntity savedEntity = ticketJpaRepository.save(ticketEntity);


        /*GUARDAR EL COMENTARIO*/
        if (Objects.nonNull(request.comentario()) && !request.comentario().isEmpty()) {
            ComentarioEntity comentario = ComentarioEntity.builder()
                    .contenido(request.comentario())
                    .esInterno(false)
                    .ticket(savedEntity)
                    .cliente(cliente)
                    .build();

            comentarioJpaRepository.save(comentario);
        }

        return ticketMapper.toResponse(savedEntity);
    }

    @Transactional
    @Override
    public TicketResponse actualizarTicket(String ticket, TicketPatchRequest request) {
        boolean hayCambios = false;
        log.info("Aplicando PATCH al ticket ID: {}", ticket);

        TecnicoEntity tecnico = null;

        /*BUSCAR SI EXISTE TICKET*/
        var ticketEntity = ticketJpaRepository.findByNumeroTicket(ticket)
                .orElseThrow(
                        () -> new TicketException("No se encontro el ticket: ", ticket)
                );

        /*VALIDAR ESTADO*/
        if (ticketEntity.getEstado().equals(EstadoEnum.CERRADO) ||
                ticketEntity.getEstado().equals(EstadoEnum.CANCELADO)
        ) {
            throw new TicketException("No se puede modificar un ticket cerrado o camcelado");
        }


        TicketEntity.TicketEntityBuilder builder = ticketEntity.toBuilder();

        /* ACTUALIZA TITULO */
        if (Objects.nonNull(request.titulo()) && !request.titulo().equals(ticketEntity.getTitulo())) {
            historialTicketCommandService.registrarHistorialTicker(
                    ticketEntity, "titulo", ticketEntity.getTitulo(), request.titulo()
            );
            builder.titulo(request.titulo());
            hayCambios = true;
        }

        /* ACTUALIZA DESCRIPCIÓN */
        if (Objects.nonNull(request.descripcion()) && !request.descripcion().equals(ticketEntity.getDescripcion())) {
            historialTicketCommandService.registrarHistorialTicker(
                    ticketEntity, "descripcion", ticketEntity.getDescripcion(), request.descripcion()
            );
            builder.descripcion(request.descripcion());
            hayCambios = true;
        }

        /* ACTUALIZA ESTADO */
        if (Objects.nonNull(request.estado())) {
            EstadoEnum nuevoEstado = EstadoEnum.getEstadoEnum(request.estado());
            if (!nuevoEstado.equals(ticketEntity.getEstado())) {
                historialTicketCommandService.registrarHistorialTicker(
                        ticketEntity, "estado", ticketEntity.getEstado().toString(),
                        nuevoEstado.toString()
                );
                builder.estado(nuevoEstado);
                if (nuevoEstado.equals(EstadoEnum.CERRADO) || nuevoEstado.equals(EstadoEnum.RESUELTO)) {
                    builder.fechaCierre(LocalDateTime.now());
                }
                hayCambios = true;
            }
        }

        /* ACTUALIZA PRIORIDAD */
        if (Objects.nonNull(request.prioridad())) {
            PrioridadEnum nuevaPrioridad = PrioridadEnum.getPrioridadEnum(request.prioridad());
            if (!nuevaPrioridad.equals(ticketEntity.getPrioridad())) {
                historialTicketCommandService.registrarHistorialTicker(
                        ticketEntity, "prioridad", ticketEntity.getPrioridad().toString(),
                        nuevaPrioridad.toString()
                );
                builder.prioridad(nuevaPrioridad);
                hayCambios = true;
            }
        }

        /* ACTUALIZA TÉCNICO */
        if (Objects.nonNull(request.tecnicoId())) {
            /* BUSCAR TECNICO */
            tecnico = tecnicoJpaRepository.findById(request.tecnicoId())
                    .orElseThrow(
                            () -> new TicketException("No se encontro el técnico: ", request.tecnicoId().toString())
                    );


            String valorAnterior = ticketEntity.getTecnicoAsignado() != null
                    ? getTecnicoFullNombre(tecnico) : "sin asignar";

            historialTicketCommandService.registrarHistorialTicker(
                    ticketEntity, "tecnico_asignado", valorAnterior, getTecnicoFullNombre(tecnico)
            );
            builder.tecnicoAsignado(tecnico);
            hayCambios = true;

        }

        /* ACTUALIZAR CATEGORIA */
        if (Objects.nonNull(request.categoriaId())) {
            var categoria = categoriaJpaRepository.findById(request.categoriaId())
                    .orElseThrow(
                            () -> new TicketException("No existe categoria: ", request.categoriaId().toString())
                    );
            String valorAnterior = ticketEntity.getCategoria().getNombre();
            String valorNuevo = categoria.getNombre();

            historialTicketCommandService.registrarHistorialTicker(
                    ticketEntity, "categoria", valorAnterior, valorNuevo
            );

            builder.categoria(categoria);
            hayCambios = true;

        }

        /* AGREGAR COMENTARIO  */
        if (Objects.nonNull(request.comentario()) && !request.comentario().isEmpty()) {
            ComentarioEntity comentario = ComentarioEntity.builder()
                    .contenido(request.comentario())
                    .esInterno(request.comentarioInterno())
                    .ticket(ticketEntity)
                    .tecnico(request.tecnicoId() != null ? tecnico : null)
                    .fechaCreacion(LocalDateTime.now())
                    .build();
            comentarioJpaRepository.save(comentario);

            historialTicketCommandService.registrarHistorialTicker(
              ticketEntity, "comentario","", request.comentario()
            );
            hayCambios = true;
        }


        /* APLICAR CAMBIOS*/
        if (!hayCambios) {
            log.info("TicketI: {} - No se detecto cambios", ticket);
        } else {
            builder.fechaActualizacion(LocalDateTime.now());
            TicketEntity ticketActualizar = builder.build();

            return ticketMapper.toResponse(ticketJpaRepository.save(ticketActualizar));
        }

        return ticketMapper.toResponse(ticketEntity);
    }

    private String generateTicketNumber() {
        return "TKT-"+System.currentTimeMillis();
//        return "TKT-" + UUID.randomUUID().toString();
    }

    private String getTecnicoFullNombre(TecnicoEntity tecnicoEntity) {
        return tecnicoEntity.getNombre() + " " + tecnicoEntity.getApellido();
    }
}
