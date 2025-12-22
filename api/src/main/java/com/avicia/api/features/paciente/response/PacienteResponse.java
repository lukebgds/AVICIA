package com.avicia.api.features.paciente.response;

import com.avicia.api.features.usuario.response.UsuarioResponse;

import lombok.Data;

@Data
public class PacienteResponse {

    private Integer idPaciente;
    private UsuarioResponse usuario;
    private String profissao;
    private String preferenciaContato;

}
