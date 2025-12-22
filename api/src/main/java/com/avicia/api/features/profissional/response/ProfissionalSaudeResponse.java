package com.avicia.api.features.profissional.response;

import com.avicia.api.features.usuario.response.UsuarioResponse;

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