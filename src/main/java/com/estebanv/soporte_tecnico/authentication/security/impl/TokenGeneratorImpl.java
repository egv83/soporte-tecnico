package com.estebanv.soporte_tecnico.authentication.security.impl;

import com.estebanv.soporte_tecnico.authentication.controller.dto.response.UserAuthentication;
import com.estebanv.soporte_tecnico.authentication.security.TokenGenerate;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenGeneratorImpl implements TokenGenerate {

    private final String jwtSecret;
    private final String companyName;
    private final Long jwtExpirtaion;


    public TokenGeneratorImpl(
            @Value("${company.name}")
            String companyName,

            @Value("${security.jwt.expiration}")
            Long jwtExpirtaion,

            @Value("${security.jwt.key}")
            String jwtSecret
    ) {
        this.companyName = companyName;
        this.jwtExpirtaion = jwtExpirtaion;
        this.jwtSecret = jwtSecret;
    }


    @Override
    public String generateToken(UserAuthentication user) {
        return Jwts.builder()
                .issuer(companyName)
                .subject(user.username())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirtaion))
                .signWith(getJwtSecret(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getJwtSecret() {
        byte[] secretByte = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(secretByte);
    }

}
