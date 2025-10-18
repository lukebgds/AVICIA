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
import com.avicia.api.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
    
    @Autowired
    private final JwtEncoder jwtEncoder;
    private final SystemLogService systemLogService;
    
    public String generate(String identity, Integer idUsuario, TokenRoleRequest role) {
        
        // Validações
        if (identity == null || identity.trim().isEmpty()) {
            throw new BusinessException("Identity não pode ser vazio");
        }
        
        if (idUsuario == null) {
            throw new BusinessException("ID do usuário não pode ser nulo");
        }
        
        if (role == null) {
            throw new BusinessException("Role não pode ser nulo");
        }
        
        if (role.getNome() == null || role.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome da role não pode ser vazio");
        }
        
        try {
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
            
        } catch (Exception e) {
            // Log de erro (Falha na geração do token)
            systemLogService.registrarErro(
                    idUsuario,
                    "Token",
                    "Falha ao gerar token JWT para usuário ID: " + idUsuario + " - Erro: " + e.getMessage()
            );
            
            throw new BusinessException("Erro ao gerar token JWT: %s", e.getMessage());
        }
    }
    
    public Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}