package com.avicia.api.data.enumerate;

public enum PreferenciaContato {

    TELEFONE("telefone"),
    EMAIL("email"),
    OUTRO("outro");

    private String preferenciaContato;

    PreferenciaContato(String PreferenciaContato) { this.preferenciaContato = preferenciaContato; }

    public String getPreferenciaContato() { return preferenciaContato; }

}
