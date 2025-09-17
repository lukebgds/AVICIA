package com.avicia.api.data.dto.object;

import lombok.Data;

@Data
public class FuncionarioDTO {

    private Integer idAdministrativo;
    private UsuarioDTO usuario;
    private String cargo;
    private String setor;
    private String matricula;
    private String observacoes;

}
