package com.avicia.api.features.clinico.exame.solicitado.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ExameSolicitadoRequest {

    private Integer idConsulta;
    private Integer idPaciente;
    private Integer idExame;
    private Integer idProfissionalSaude;
    private LocalDate dataSolicitacao;
    private String observacoes;
    private String status;

}
