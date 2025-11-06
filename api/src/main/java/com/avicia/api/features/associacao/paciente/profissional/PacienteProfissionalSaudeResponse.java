package com.avicia.api.features.associacao.paciente.profissional;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteProfissionalSaudeResponse {

    private Integer idProfissional;
    private String nomeProfissional;
    private String especialidade;
    private String unidade;

    private Integer idPaciente;
    private String nomePaciente;

    private LocalDateTime dataVinculo;
    
}
