package com.avicia.api.features.paciente.dados.antecedente.request;

import lombok.Data;

@Data
public class PacienteAntecedenteRequest {

    private Integer idPaciente;
    private String tipoDoenca;
    private String parentesco;

}
