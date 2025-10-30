package com.avicia.api.features.clinico.internacao.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class InternacaoResponse {

    private Integer idInternacao;
    private Integer idPaciente;
    private Integer idProfissionalSaude;
    private LocalDate dataAdmissao;
    private LocalDate dataAlta;
    private String leito;
    private String observacoes;

}
