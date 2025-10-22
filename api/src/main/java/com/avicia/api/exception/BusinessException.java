package com.avicia.api.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final Integer idUsuario;
    private final String contexto;

    public BusinessException(String mensage) { 
        super(mensage);
        this.idUsuario = null;
        this.contexto = null;
    }
    
    public BusinessException(String mensage, Object ... params) { 
        super(String.format(mensage, params));
        this.idUsuario = null;
        this.contexto = null;
    }

    public BusinessException(Integer idUsuario, String contexto, String mensage, Object ... params) { 
        super(String.format(mensage, params));
        this.idUsuario = idUsuario;
        this.contexto = contexto;
    }

}