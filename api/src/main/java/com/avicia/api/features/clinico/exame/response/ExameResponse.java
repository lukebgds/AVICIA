package com.avicia.api.features.clinico.exame.response;

import lombok.Data;

@Data
public class ExameResponse {

    private Integer idExame;
    private String nome;
    private String descricao;
    private String tipo;
    private Boolean ativo;

}
