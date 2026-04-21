package com.estebanv.soporte_tecnico.cliente.controller;

import com.estebanv.soporte_tecnico.cliente.controller.request.ClienteCreateRequest;
import com.estebanv.soporte_tecnico.cliente.controller.request.ClienteUpdateRequest;
import com.estebanv.soporte_tecnico.cliente.service.ClienteCommandService;
import com.estebanv.soporte_tecnico.cliente.service.ClienteQueryService;
import com.estebanv.soporte_tecnico.user.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteQueryService clienteQueryService;
    private final ClienteCommandService clienteCommandService;

    public ClienteController(ClienteQueryService clienteQueryService, ClienteCommandService clienteCommandService) {
        this.clienteQueryService = clienteQueryService;
        this.clienteCommandService = clienteCommandService;
    }

    @GetMapping
    public ResponseEntity<?> getAllClientes(){

        var clientes = clienteQueryService.getAllClientes();

        if(clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(
            @PathVariable Long id
    ){
        var cliente = clienteQueryService.getClienteById(id);

        if(Objects.isNull(cliente)){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClienteCreateRequest request){
        return new ResponseEntity<>(clienteCommandService.create(request), HttpStatus.CREATED);
    }

}
