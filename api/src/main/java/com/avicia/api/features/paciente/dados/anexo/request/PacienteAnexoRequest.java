package com.avicia.api.features.paciente.dados.anexo.request;

import lombok.Data;

@Data
public class PacienteAnexoRequest {

    private Integer idPaciente;
    private String tipo;
    private String descricao;
    private String urlArquivo;

}
