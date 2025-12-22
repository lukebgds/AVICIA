package com.avicia.api.features.clinico.consulta.response;

import java.time.LocalDateTime;

import com.avicia.api.data.enumerate.TipoConsulta;

import lombok.Data;

@Data
public class ConsultaResponse {

    private Integer idConsulta;
    private Integer idPaciente;
    private Integer idProfissionalSaude;
    private LocalDateTime dataConsulta;
    private TipoConsulta tipoConsulta;
    private String localConsulta;
    private String anamnese;
    private String observacoes;

}
