package com.avicia.api.data.dto.response.profissional;

import com.avicia.api.data.dto.response.usuario.UsuarioResponse;

import lombok.Data;

@Data
public class ProfissionalSaudeResponse {

	private Integer idProfissional;
	private UsuarioResponse usuario;
    private String matricula;
    private String conselho;
    private String registroConselho;
    private String especialidade;
    private String cargo;
    private String unidade;
	
}