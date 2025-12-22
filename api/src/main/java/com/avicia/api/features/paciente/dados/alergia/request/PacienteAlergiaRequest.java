package com.avicia.api.features.paciente.dados.alergia.request;

import com.avicia.api.data.enumerate.Gravidade;

import lombok.Data;

@Data
public class PacienteAlergiaRequest {

    private Integer idPaciente;
    private Gravidade gravidade;
    private String descricao;

}
