package com.estebanv.soporte_tecnico.authentication.security.config;

import com.estebanv.soporte_tecnico.user.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final String jwtSecret;
    private final UsuarioService usuarioService;

    public JwtAuthenticationFilter(
            @Value("${security.jwt.key}")
            String jwtSecret,
            UsuarioService usuarioService
    ) {
        this.jwtSecret = jwtSecret;
        this.usuarioService = usuarioService;
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getRequestURI();
//
//        // Lista de rutas que NO deben pasar por el filtro JWT
//        boolean isPublicPath = path.contains("/auth/")
//                || path.contains("/login")
//                || path.contains("/register");
//
//        if (isPublicPath) {
//            log.info("🔓 Ruta pública, excluyendo del filtro JWT: {}", path);
//        }
//
//        return isPublicPath;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("=== FILTRO JWT EJECUTÁNDOSE PARA: {} ===", request.getRequestURI());

        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            log.warn("No hay token de autenticación");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = tokenHeader.substring(7);
        Claims claims = (Claims) Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();

        var userName = getSpecificClaims(claims, Claims::getSubject);
        var userOpt = usuarioService.validarCredenciales(userName);
        if (userOpt == null) {
            logger.error("No existe el usuario propietario del token");
            filterChain.doFilter(request, response);
            return;
        }

        if (!userOpt.isActived() || !userOpt.isAccountNonLocked()) {
            log.warn("Usuario no activado o bloqueado: {}", userName);
            filterChain.doFilter(request, response);
            return;
        }

        var userDetails = MyCompanyUserDetails.builder()
                .username(userOpt.userName())
                .password(null)
                .build();

        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(upat);
        MDC.put("userName", userOpt.userName());
        filterChain.doFilter(request, response);

    }

    private String getSpecificClaims(Claims claims, Function<Claims, String> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    private SecretKey getSigninKey() {
        byte[] secretByte = Decoders.BASE64URL.decode(jwtSecret);
        return Keys.hmacShaKeyFor(secretByte);
    }
}
