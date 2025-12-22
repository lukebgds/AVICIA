package com.avicia.api.features.paciente.dados.convenio.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PacienteConvenioResponse {

    private Integer idConvenio;
    private Integer idPaciente;
    private String nomeConvenio;
    private String numeroCarteirinha;
    private LocalDate validade;

}
