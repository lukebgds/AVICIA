package com.avicia.api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.request.role.TokenRoleRequest;
import com.avicia.api.security.verify.VerificarToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
    
    private final JwtEncoder jwtEncoder;
    private final SystemLogService systemLogService;
    private final VerificarToken verificarToken;
    
    public String generate(String identity, Integer idUsuario, TokenRoleRequest role) {
        // Validações
        verificarToken.validarCamposObrigatoriosToken(identity, idUsuario, role);
        
        var claims = JwtClaimsSet.builder()
                .issuer("avicia.api")
                .subject(identity)
                .expiresAt(generateExpirationDate())
                .issuedAt(Instant.now())
                .claim("userId", idUsuario)
                .claim("role", role.getNome())
                .claim("permissoes", role.getPermissoes())
                .build();
        
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        
        // Registro de log (Token gerado com sucesso)
        systemLogService.registrarCriacao(
                idUsuario,
                "Token",
                "Token JWT gerado com sucesso para usuário ID: " + idUsuario + " com role: " + role.getNome()
        );
        
        return jwtValue;
    }
    
    public Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}