package com.avicia.api.features.associacao.paciente.funcionario;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteFuncionarioResponse {

    private Integer idFuncionario;
    private String nomeFuncionario;
    private String cargoFuncionario;
    private String setorFuncionario;

    private Integer idPaciente;
    private String nomePaciente;
    private String emailPaciente;

    private LocalDateTime dataVinculo;

}
