package com.avicia.api.features.paciente.dados.medicamento.response;

import lombok.Data;

@Data
public class PacienteMedicamentoResponse {

    private Integer idMedicamento;
    private Integer idPaciente;
    private String medicamento;
    private String dosagem;
    private String frequencia;

}
