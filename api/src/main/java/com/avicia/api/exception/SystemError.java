package com.avicia.api.exception;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SystemError {

    /**
     * Lança uma BusinessException com ID e contexto padrão ("Usuario")
     */
    public void error(String mensagem, Object... params) {
        throw new BusinessException(100, "Sistema", mensagem, params);
    }

    /**
     * Lança uma BusinessException
     */
    public void error(Integer idUsuario, String contexto, String mensagem, Object... params) {
        throw new BusinessException(
            idUsuario != null ? idUsuario : 100,
            contexto != null ? contexto : "Usuario",
            mensagem, params
        );
    }

}
