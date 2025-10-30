package com.avicia.api.features.funcionario.request;

import lombok.Data;

@Data
public class FuncionarioRequest {

    private Integer idUsuario;
    private String cargo;
    private String setor;
    private String matricula;
    private String observacoes;

}
