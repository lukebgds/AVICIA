package com.avicia.api.features.clinico.exame.request;

import lombok.Data;

@Data
public class ExameRequest {

    private String nome;
    private String descricao;
    private String tipo;
    private Boolean ativo;

}
