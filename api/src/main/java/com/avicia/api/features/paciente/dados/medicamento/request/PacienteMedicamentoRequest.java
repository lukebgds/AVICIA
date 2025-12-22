package com.avicia.api.features.paciente.dados.medicamento.request;

import lombok.Data;

@Data
public class PacienteMedicamentoRequest {

    private Integer idPaciente;
    private String medicamento;
    private String dosagem;
    private String frequencia;

}
