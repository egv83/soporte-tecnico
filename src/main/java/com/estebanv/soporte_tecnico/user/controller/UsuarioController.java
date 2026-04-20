package com.estebanv.soporte_tecnico.user.controller;

import com.estebanv.soporte_tecnico.user.controller.dto.request.UserUpdateRequest;
import com.estebanv.soporte_tecnico.user.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request
    ){
        usuarioService.updateUserPassword(request,id);
        return ResponseEntity.ok().build();
    }


}
