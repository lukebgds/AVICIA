package com.avicia.api.data.dto.response.funcionario;

import com.avicia.api.data.dto.response.usuario.UsuarioResponse;

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
