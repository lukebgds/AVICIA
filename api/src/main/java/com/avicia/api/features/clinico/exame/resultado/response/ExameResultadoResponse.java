package com.avicia.api.features.clinico.exame.resultado.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ExameResultadoResponse {

    private Integer idResultado;
    private Integer idExameSolicitado;
    private String nomeExame;
    private LocalDate dataResultado;
    private String laudo;
    private String arquivoResultado;
    private String observacoes;
    private Integer idAssinadoPor;
    private String status;

}
