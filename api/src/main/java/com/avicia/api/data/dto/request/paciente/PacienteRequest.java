package com.avicia.api.data.dto.request.paciente;

import lombok.Data;

@Data
public class PacienteRequest {

    private Integer idUsuario; 
    private String profissao;
    private String preferenciaContato;

}
