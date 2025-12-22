package com.avicia.api.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDiagnostico {

    PRINCIPAL("principal"),
    SECUNDARIO("secundario");

    private String tipoDiagnostico;

}
