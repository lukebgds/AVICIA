package com.avicia.api.data.dto.response.paciente;

import com.avicia.api.data.dto.response.usuario.UsuarioResponse;

import lombok.Data;

@Data
public class PacienteResponse {

    private Integer idPaciente;
    private UsuarioResponse usuario;
    private String profissao;
    private String preferenciaContato;

}
