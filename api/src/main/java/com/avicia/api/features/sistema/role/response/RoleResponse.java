package com.avicia.api.features.sistema.role.response;

import java.util.Map;

import lombok.Data;

@Data
public class RoleResponse {
    private Integer idRole;
    private String nome;
    private String descricao;
    private Map<String, Object> permissoes;
}
