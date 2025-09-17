package com.avicia.api.data.dto.object;


import lombok.Data;

@Data
public class RoleDTO {

    private Integer idRole;
    private String nome;
    private String descricao;
    private String permissoes;

}
