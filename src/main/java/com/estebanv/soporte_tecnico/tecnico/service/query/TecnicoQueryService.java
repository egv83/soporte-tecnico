package com.estebanv.soporte_tecnico.tecnico.service.query;

import com.estebanv.soporte_tecnico.tecnico.controller.dto.response.TecnicoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TecnicoQueryService {

    Page<TecnicoResponse> getAllTecnicos(Pageable pageable);
    TecnicoResponse getTecnicoById(Long id);

}
