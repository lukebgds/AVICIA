package com.avicia.api.data.dto.request;

import lombok.Data;

@Data
public class ProfissionalSaudeRequest {

	private Integer idUsuario;
    private String matricula;
    private String registroConselho;
    private String especialidade;
    private String cargo;
    private String unidade;

}