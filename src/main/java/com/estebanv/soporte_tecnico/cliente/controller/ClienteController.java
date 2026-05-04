package com.estebanv.soporte_tecnico.cliente.controller;

import com.estebanv.soporte_tecnico.cliente.controller.request.ClienteCreateRequest;
import com.estebanv.soporte_tecnico.cliente.controller.request.ClienteUpdateRequest;
import com.estebanv.soporte_tecnico.cliente.entities.ClienteEntity;
import com.estebanv.soporte_tecnico.cliente.service.ClienteCommandService;
import com.estebanv.soporte_tecnico.cliente.service.ClienteQueryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {

    private final ClienteQueryService clienteQueryService;
    private final ClienteCommandService clienteCommandService;

    public ClienteController(ClienteQueryService clienteQueryService, ClienteCommandService clienteCommandService) {
        this.clienteQueryService = clienteQueryService;
        this.clienteCommandService = clienteCommandService;
    }

    @GetMapping
    public ResponseEntity<?> getAllClientes() {

        var clientes = clienteQueryService.getAllClientes();

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/entity-page")
    public ResponseEntity<Page<ClienteEntity>> getClientePage(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of( ((page>=1) ? page-1 : 0) ,size );
         var clientes = clienteQueryService.getAllClientes(pageable);

         if(clientes.isEmpty()){
             return ResponseEntity.noContent().build();
         }

         return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(
            @PathVariable Long id
    ) {
        var cliente = clienteQueryService.getClienteById(id);

        if (Objects.isNull(cliente)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClienteCreateRequest request) {
        return new ResponseEntity<>(clienteCommandService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @RequestBody ClienteUpdateRequest request,
            @PathVariable("id") Long id
    ) {
        clienteCommandService.update(request, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable("id") Long id) {

        clienteCommandService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
