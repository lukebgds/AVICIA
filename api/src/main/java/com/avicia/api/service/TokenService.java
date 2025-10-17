package com.avicia.api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.request.role.TokenRoleRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Autowired
    private final JwtEncoder jwtEncoder;

    public String generate(String identity, TokenRoleRequest role) {

        var claims = JwtClaimsSet.builder()
                .issuer("avicia.api")
                .subject(identity)
                .expiresAt(generateExpirationDate())
                .issuedAt(Instant.now())
                .claim("role", role.getNome())
                .claim("permissoes", role.getPermissoes())
                .build();
        
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return jwtValue;
    }

    public Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


}
