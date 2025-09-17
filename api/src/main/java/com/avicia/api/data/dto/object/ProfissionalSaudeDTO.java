package com.avicia.api.data.dto.object;

import lombok.Data;

@Data
public class ProfissionalSaudeDTO {

    private Integer idProfissional;
    private UsuarioDTO usuario;
    private String matricula;
    private String registroConselho;
    private String especialidade;
    private String cargo;
    private String unidade;

}
