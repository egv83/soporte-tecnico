package com.estebanv.soporte_tecnico.common.security.impl;

import com.estebanv.soporte_tecnico.common.security.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderImpl implements PasswordEncoder {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordEncoderImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String requestPassword, String password) {
        return passwordEncoder.matches(requestPassword, password);
    }
}
