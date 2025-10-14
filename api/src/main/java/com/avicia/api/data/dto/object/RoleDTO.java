package com.avicia.api.data.dto.object;


import java.util.Map;

import lombok.Data;

@Data
public class RoleDTO {

    private Integer idRole;
    private String nome;
    private String descricao;
    private Map<String, Object> permissoes;

}
