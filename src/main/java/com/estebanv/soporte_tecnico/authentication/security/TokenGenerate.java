package com.estebanv.soporte_tecnico.authentication.security;

import com.estebanv.soporte_tecnico.authentication.controller.dto.response.UserAuthentication;

public interface TokenGenerate {

    String generateToken(UserAuthentication userAuthentication);

}
