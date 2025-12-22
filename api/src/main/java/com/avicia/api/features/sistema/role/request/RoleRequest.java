package com.avicia.api.features.sistema.role.request;

import java.util.Map;

import lombok.Data;

@Data
public class RoleRequest {

    private String nome;
    private Integer idTipoRole;
    private String descricao;
    private Map<String, Object> permissoes;

}
