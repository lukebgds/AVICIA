package com.avicia.api.features.paciente.dados.convenio.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PacienteConvenioRequest {

    private Integer idPaciente;
    private String nomeConvenio;
    private String numeroCarteirinha;
    private LocalDate validade;

}
