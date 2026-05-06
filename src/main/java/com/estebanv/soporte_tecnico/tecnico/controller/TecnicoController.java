package com.estebanv.soporte_tecnico.tecnico.controller;

import com.estebanv.soporte_tecnico.tecnico.controller.dto.request.TecnicoCreateRequest;
import com.estebanv.soporte_tecnico.tecnico.controller.dto.request.TecnicoUpdateRequest;
import com.estebanv.soporte_tecnico.tecnico.controller.dto.response.TecnicoResponse;
import com.estebanv.soporte_tecnico.tecnico.service.command.TecnicoCommandService;
import com.estebanv.soporte_tecnico.tecnico.service.query.TecnicoQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/v1/tecnicos")
@RequiredArgsConstructor
public class TecnicoController {

    private final TecnicoQueryService tecnicoQueryService;
    private final TecnicoCommandService tecnicoCommandService;

    @GetMapping("/dto-page")
    public ResponseEntity<Page<TecnicoResponse>> getAllTecnicos(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(((page >= 1) ? page - 1 : 0), size);

        var tecnicos = tecnicoQueryService.getAllTecnicos(pageable);

        if(tecnicos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tecnicos);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getTecnicoById(@PathVariable(name = "id") Long id) {
        var tecnico = tecnicoQueryService.getTecnicoById(id);

        if(Objects.isNull(tecnico)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tecnico);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TecnicoCreateRequest request){
        return new ResponseEntity<>(tecnicoCommandService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid
            @RequestBody TecnicoUpdateRequest request,
            @PathVariable("id") Long id
    ) {
        tecnicoCommandService.update(request, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        tecnicoCommandService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
