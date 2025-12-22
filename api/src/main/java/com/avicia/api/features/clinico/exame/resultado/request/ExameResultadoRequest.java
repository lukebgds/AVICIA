package com.avicia.api.features.clinico.exame.resultado.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ExameResultadoRequest {

    private Integer idExameSolicitado;
    private LocalDate dataResultado;
    private String laudo;
    private String arquivoResultado;
    private String observacoes;
    private Integer idAssinadoPor;
    private String status;

}
