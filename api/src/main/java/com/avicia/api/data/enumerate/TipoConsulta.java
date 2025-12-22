package com.avicia.api.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoConsulta {

    PRESENCIAL("presencial"),
    TELECONSULTA("teleconsulta");

    private String tipoConsulta;
}
