package com.avicia.api.security.verify;

import org.springframework.stereotype.Component;

import com.avicia.api.data.dto.request.role.TokenRoleRequest;
import com.avicia.api.exception.SystemError;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarToken {

    private final SystemError systemError;

    /**
     * Valida se o identity não é nulo ou vazio
     */
    public void validarIdentityNaoVazio(String identity) {
        if (identity == null || identity.trim().isEmpty()) {
            systemError.error("Identity não pode ser vazio");
        }
    }

    /**
     * Valida se o ID do usuário não é nulo
     */
    public void validarIdUsuarioNaoNulo(Integer idUsuario) {
        if (idUsuario == null) {
            systemError.error("ID do usuário não pode ser nulo");
        }
    }

    /**
     * Valida se a role não é nula
     */
    public void validarRoleNaoNula(TokenRoleRequest role) {
        if (role == null) {
            systemError.error("Role não pode ser nulo");
        }
    }

    /**
     * Valida se o nome da role não é nulo ou vazio
     */
    public void validarNomeRoleNaoVazio(String nomeRole) {
        if (nomeRole == null || nomeRole.trim().isEmpty()) {
            systemError.error("Nome da role não pode ser vazio");
        }
    }

    /**
     * Valida todos os campos obrigatórios para geração de token
     */
    public void validarCamposObrigatoriosToken(String identity, Integer idUsuario, TokenRoleRequest role) {
        validarIdentityNaoVazio(identity);
        validarIdUsuarioNaoNulo(idUsuario);
        validarRoleNaoNula(role);
        validarNomeRoleNaoVazio(role.getNome());
    }
}