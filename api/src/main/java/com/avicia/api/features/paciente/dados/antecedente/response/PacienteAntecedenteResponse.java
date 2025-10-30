package com.avicia.api.features.paciente.dados.antecedente.response;

import lombok.Data;

@Data
public class PacienteAntecedenteResponse {

    private Integer idAntecedente;
    private Integer idPaciente;
    private String tipoDoenca;
    private String parentesco;

}
