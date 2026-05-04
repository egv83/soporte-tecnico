package com.estebanv.soporte_tecnico.tecnico.service.command;

import com.estebanv.soporte_tecnico.tecnico.controller.dto.request.TecnicoCreateRequest;
import com.estebanv.soporte_tecnico.tecnico.controller.dto.request.TecnicoUpdateRequest;
import com.estebanv.soporte_tecnico.tecnico.controller.dto.response.TecnicoResponse;

public interface TecnicoCommandService {

    TecnicoResponse create(TecnicoCreateRequest request);

    void update(TecnicoUpdateRequest request, Long id);

    void delete(Long id);

}
