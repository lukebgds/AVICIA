package com.avicia.api.data.enumerate;

import lombok.Getter;

@Getter
public enum TipoSystemLog {

    MODIFICACAO("MODIFICAÇÃO"),
    CRIACAO("CRIAÇÃO"),
    EXCLUSAO("EXCLUSÃO"),
    AVISO("AVISO"),
    ERRO("ERRO");

    private String tipoSystemLog;

    TipoSystemLog(String tipoSystemLog) { this.tipoSystemLog = tipoSystemLog; }

}
