package com.estebanv.soporte_tecnico.common.security;

public interface PasswordEncoder {
    String encode(String password);
    boolean matches(String requestPassword, String password);
}
