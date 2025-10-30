package com.avicia.api.features.paciente.dados.vacina.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PacienteVacinaRequest {

    private Integer idPaciente;
    private String vacina;
    private LocalDateTime dataAplicacao;

}
