package com.avicia.api.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gravidade {

    LEVE("leve"),
    MODERADA("moderada"),
    GRAVE("grave");

    private String gravidade; 

}
