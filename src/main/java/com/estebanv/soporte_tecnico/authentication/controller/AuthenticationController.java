package com.estebanv.soporte_tecnico.authentication.controller;

import com.estebanv.soporte_tecnico.authentication.controller.dto.request.LoginRequest;
import com.estebanv.soporte_tecnico.authentication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated  @RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

}
