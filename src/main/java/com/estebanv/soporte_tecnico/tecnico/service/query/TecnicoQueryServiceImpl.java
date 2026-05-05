package com.estebanv.soporte_tecnico.tecnico.service.query;

import com.estebanv.soporte_tecnico.cliente.exception.ClienteException;
import com.estebanv.soporte_tecnico.exceptions.ResourceNotFoundException;
import com.estebanv.soporte_tecnico.tecnico.controller.dto.response.TecnicoResponse;
import com.estebanv.soporte_tecnico.tecnico.entities.TecnicoEntity;
import com.estebanv.soporte_tecnico.tecnico.exception.TecnicoException;
import com.estebanv.soporte_tecnico.tecnico.repositories.TecnicoJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TecnicoQueryServiceImpl implements TecnicoQueryService {

    private final TecnicoJpaRepository tecnicoJpaRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<TecnicoResponse> getAllTecnicos(Pageable pageable) {

        log.info("***** CONSULTA DE TODOS LOS TECNICOS PAGINADO *****");
        log.debug("Pageable: {}", pageable);


        Page<TecnicoEntity> tecnicosPage = tecnicoJpaRepository.findAll(pageable);

        List<TecnicoResponse> response = tecnicosPage.getContent()
                .stream()
                .map(tecnico -> modelMapper.map(tecnico, TecnicoResponse.class))
                .toList();

        return new PageImpl<>(response, pageable, tecnicosPage.getTotalElements());


    }

    @Override
    public TecnicoResponse getTecnicoById(Long id) {
        log.info("***** CONSULTA DE TECNICO POR ID *****");

        return tecnicoJpaRepository.findByIdActivo(id).
                map(
                        entity -> {
                            if (!entity.getIsActivo()) {
                                throw new TecnicoException("El Técnico esta inactivo");
                            }
                            return modelMapper.map(entity, TecnicoResponse.class);
                        }
                )
                .orElseThrow(
                        () -> {
                            log.warn("Técnoco no encontrado con id: {}", id);
                            return new ResourceNotFoundException("Técnico", id);
                        }
                );
    }


}
