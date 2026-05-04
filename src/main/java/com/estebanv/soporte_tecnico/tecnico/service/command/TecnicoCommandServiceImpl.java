package com.estebanv.soporte_tecnico.tecnico.service.command;

import com.estebanv.soporte_tecnico.cliente.exception.ClienteException;
import com.estebanv.soporte_tecnico.tecnico.controller.dto.request.TecnicoCreateRequest;
import com.estebanv.soporte_tecnico.tecnico.controller.dto.request.TecnicoUpdateRequest;
import com.estebanv.soporte_tecnico.tecnico.controller.dto.response.TecnicoResponse;
import com.estebanv.soporte_tecnico.tecnico.entities.TecnicoEntity;
import com.estebanv.soporte_tecnico.tecnico.exception.TecnicoException;
import com.estebanv.soporte_tecnico.tecnico.repositories.TecnicoJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TecnicoCommandServiceImpl implements TecnicoCommandService {

    private final TecnicoJpaRepository tecnicoJpaRepository;
    private final ModelMapper modelMapper;


    @Override
    public TecnicoResponse create(TecnicoCreateRequest request) {
        log.info("****** CREAR TÉCNICO *****");
        var tecnico = tecnicoJpaRepository.findByIdentificacion(request.identificacion());

        if (tecnico.isPresent()) {
            log.warn("El técnico ya existe con identificación: {}", request.identificacion());
            throw new TecnicoException("El técnico ya existe con identificación: %s", request.identificacion());
        }

        return modelMapper.map(tecnicoJpaRepository.save(
                TecnicoEntity.builder()
                        .identificacion(request.identificacion())
                        .nombre(request.nombre())
                        .apellido(request.apellido())
                        .telefono(request.telefono())
                        .isActived(Boolean.TRUE)
                        .fechaRegistro(LocalDateTime.now())
                        .build()
        ), TecnicoResponse.class);

    }

    @Override
    public void update(TecnicoUpdateRequest request, Long id) {
        log.info("****** ACTULIZAR TECNICO *****");

        TecnicoEntity tecnico = tecnicoJpaRepository.findById(id)
                .orElseThrow(() -> {
                            log.warn("Técnico no encontrado con id: {}", id);
                            throw new ClienteException("Técnico no encontrado con id: %s", String.valueOf(id));
                        }
                );

        TecnicoEntity tecnicoUpdate = tecnico.toBuilder()
                .nombre(StringUtils.hasText(request.nombre()) ? request.nombre() :
                        tecnico.getNombre())
                .apellido(StringUtils.hasText(request.apellido()) ? request.apellido() :
                        tecnico.getApellido())
                .telefono(StringUtils.hasText(request.telefono()) ? request.telefono() :
                        tecnico.getTelefono())
                .isActived(Objects.nonNull(request.activo()) ? request.activo() :
                        tecnico.getIsActived())
                .build();

        System.out.println("CLIENTE UPDATE: " + tecnicoUpdate);

        tecnicoJpaRepository.save(tecnicoUpdate);
    }

    @Override
    public void delete(Long id) {
        log.info("****** ELIMINAR TECNICO *****");
        log.info("Delete tecnico con id {}", id);

        TecnicoEntity tecnico = tecnicoJpaRepository.findById(id)
                .orElseThrow(() -> {
                            log.warn("Técnico no encontrado con id: {}", id);
                            throw new ClienteException("Tpecnico no encontrado con id: %s", String.valueOf(id));
                        }
                );

        log.info("TÉCNICO OBTENIDO: {}", tecnico.getId());
        TecnicoEntity tecnicoDisabled = tecnico.toBuilder()
                .isActived(false)
                .build();

        log.info("TÉCNICO ACTUALIZADO: {}", tecnicoDisabled.getId());

        tecnicoJpaRepository.save(tecnicoDisabled);

    }
}
