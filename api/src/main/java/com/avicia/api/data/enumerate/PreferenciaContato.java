package com.avicia.api.data.enumerate;

public enum PreferenciaContato {

    TELEFONE("telefone"),
    EMAIL("email"),
    SMS("sms");

    private String preferenciaContato;

    PreferenciaContato(String PreferenciaContato) { this.preferenciaContato = preferenciaContato; }

    public String getPreferenciaContato() { return preferenciaContato; }

}
