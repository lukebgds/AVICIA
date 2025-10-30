package com.avicia.api.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPrescricao {

    ATIVA("ativa"),
    SUSPENSA("suspensa"),
    CONCLUIDA("concluida");

    private String statusPrescricao;

}
