package com.avicia.api.data.dto.request;

import java.util.Map;

import lombok.Data;

@Data
public class RoleResquest {

    private Integer idRole;
    private String nome;
    private String descricao;
    private Map<String, Object> permissoes;

}
