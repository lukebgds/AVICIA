package com.avicia.api.data.dto.request;

import lombok.Data;

@Data
public class FuncionarioRequest {

    private Integer idUsuario;
    private String cargo;
    private String setor;
    private String matricula;
    private String observacoes;

}
