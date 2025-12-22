package com.avicia.api.features.paciente.dados.vacina.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PacienteVacinaResponse {

    private Integer idVacina;
    private Integer idPaciente;
    private String vacina;
    private LocalDateTime dataAplicacao;

}
