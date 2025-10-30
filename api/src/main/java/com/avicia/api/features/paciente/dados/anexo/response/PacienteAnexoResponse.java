package com.avicia.api.features.paciente.dados.anexo.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PacienteAnexoResponse {

    private Integer idAnexo;
    private Integer idPaciente;
    private String tipo;
    private String descricao;
    private String urlArquivo;
    private LocalDateTime dataUpload;

}
