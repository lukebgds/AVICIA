package com.avicia.api.features.paciente.dados.alergia.response;

import com.avicia.api.data.enumerate.Gravidade;

import lombok.Data;

@Data
public class PacienteAlergiaResponse {

    private Integer idAlergia;
    private Integer idPaciente;
    private Gravidade gravidade;
    private String descricao;

}
