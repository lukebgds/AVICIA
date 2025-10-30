package com.avicia.api.features.clinico.exame.solicitado.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ExameSolicitadoResponse {

    private Integer idExameSolicitado;
    private Integer idConsulta;
    private Integer idPaciente;
    private Integer idExame;
    private String nomeExame;
    private Integer idProfissionalSaude;
    private LocalDate dataSolicitacao;
    private String observacoes;
    private String status;

}
