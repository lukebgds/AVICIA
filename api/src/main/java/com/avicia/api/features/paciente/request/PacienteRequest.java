package com.avicia.api.features.paciente.request;

import lombok.Data;

@Data
public class PacienteRequest {

    private Integer idUsuario; 
    private String profissao;
    private String preferenciaContato;

}
