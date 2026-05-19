package com.estebanv.soporte_tecnico.authentication.service;

import com.estebanv.soporte_tecnico.authentication.controller.dto.request.LoginRequest;
import com.estebanv.soporte_tecnico.authentication.controller.dto.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

}
