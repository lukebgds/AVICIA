package com.avicia.api.features.clinico.consulta.prescricao.response;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.StatusPrescricao;

import lombok.Data;

@Data
public class ConsultaPrescricaoResponse {

    private Integer idPrescricao;
    private Integer idConsulta;
    private LocalDate dataEmissao;
    private String observacoes;
    private StatusPrescricao status;

}
