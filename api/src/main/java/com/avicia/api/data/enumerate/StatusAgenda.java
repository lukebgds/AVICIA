package com.avicia.api.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusAgenda {

    LIVRE("livre"),
    AGENDADO("agendado"),
    CONCLUIDO("concluido"),
    CANCELADO("cancelado"),
    BLOQUEADO("bloqueado"),
    EXPIRADO("expirado");

    private String statusAgenda;
}
