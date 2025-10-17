package com.avicia.api.data.dto.request.profissional;

import lombok.Data;

@Data
public class ProfissionalSaudeRequest {

	private Integer idUsuario;
    private String matricula;
    private String conselho;
    private String registroConselho;
    private String especialidade;
    private String cargo;
    private String unidade;

}