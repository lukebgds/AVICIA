package com.avicia.api.features.clinico.consulta.prescricao.request;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.StatusPrescricao;

import lombok.Data;

@Data
public class ConsultaPrescricaoRequest {

    private Integer idConsulta;
    private LocalDate dataEmissao;
    private String observacoes;
    private StatusPrescricao status;

}
