package com.avicia.api.features.clinico.consulta.request;

import java.time.LocalDateTime;

import com.avicia.api.data.enumerate.TipoConsulta;

import lombok.Data;

@Data
public class ConsultaRequest {

    private Integer idPaciente;
    private Integer idProfissionalSaude;
    private LocalDateTime dataConsulta;
    private TipoConsulta tipoConsulta;
    private String localConsulta;
    private String anamnese;
    private String observacoes;

}
