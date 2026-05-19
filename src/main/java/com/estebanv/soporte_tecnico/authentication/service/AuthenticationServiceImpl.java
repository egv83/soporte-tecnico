package com.estebanv.soporte_tecnico.authentication.service;

import com.estebanv.soporte_tecnico.authentication.controller.dto.request.LoginRequest;
import com.estebanv.soporte_tecnico.authentication.controller.dto.response.LoginResponse;
import com.estebanv.soporte_tecnico.authentication.controller.dto.response.UserAuthentication;
import com.estebanv.soporte_tecnico.authentication.security.TokenGenerate;
import com.estebanv.soporte_tecnico.common.security.PasswordEncoder;
import com.estebanv.soporte_tecnico.user.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerate tokenGenerate;

    public AuthenticationServiceImpl(UsuarioService usuarioService, PasswordEncoder passwordEncoder, TokenGenerate tokenGenerate) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerate = tokenGenerate;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("PROCESO DE LOGIN ");

        var usr = usuarioService.validarCredenciales(request.userName());

        /*validar password*/

        /*SE ASIGNA LOS DATOS QUE VIENEN DEL SERVICE DE USER AL OBJETO QUE TRABAJA CON LOS DATOS*/
        UserAuthentication userAuthentication = new UserAuthentication(
                usr.id(),
                usr.userName(),
                usr.password(),
                usr.isActived()
        );

        if (!passwordEncoder.matches(request.password(), usr.password())) {
            throw new RuntimeException("Clave incorrecta");
        }

        var token = tokenGenerate.generateToken(userAuthentication);

        return new LoginResponse(userAuthentication.username(),
                token
        );

    }


}
