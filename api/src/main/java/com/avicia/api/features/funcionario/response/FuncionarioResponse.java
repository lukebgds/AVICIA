package com.avicia.api.features.funcionario.response;

import com.avicia.api.features.usuario.response.UsuarioResponse;

import lombok.Data;

@Data
public class FuncionarioResponse {

    private Integer idFuncionario;
    private UsuarioResponse usuario; 
    private String cargo;
    private String setor;
    private String matricula;
    private String observacoes;

}
