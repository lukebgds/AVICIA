package com.avicia.api.features.clinico.internacao.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class InternacaoRequest {

    private Integer idPaciente;
    private Integer idProfissionalSaude;
    private LocalDate dataAdmissao;
    private LocalDate dataAlta;
    private String leito;
    private String observacoes;

}
