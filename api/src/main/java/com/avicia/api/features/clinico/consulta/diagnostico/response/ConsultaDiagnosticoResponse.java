package com.avicia.api.features.clinico.consulta.diagnostico.response;

import lombok.Data;

@Data
public class ConsultaDiagnosticoResponse {

    private Integer idDiagnostico;
    private Integer idConsulta;
    private String codigoCidDez;
    private String descricao;

}
