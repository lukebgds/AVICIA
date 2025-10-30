package com.avicia.api.features.clinico.consulta.diagnostico.request;

import lombok.Data;

@Data
public class ConsultaDiagnosticoRequest {

    private Integer idConsulta;
    private String codigoCidDez;
    private String descricao;

}
