package com.avicia.api.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusDiagnostico {

    ATIVO("ativo"),
    CURADO("curado"),
    INATIVO("inativo");

    private String statusDiagnostico;

}
