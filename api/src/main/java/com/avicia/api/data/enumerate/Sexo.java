package com.avicia.api.data.enumerate;

public enum Sexo {

    M("m"),
    F("f"),
    OUTRO("outro");

    private String sexo;

    Sexo(String sexo) { this.sexo = sexo; }

    public String getSexo() { return sexo; }

}
