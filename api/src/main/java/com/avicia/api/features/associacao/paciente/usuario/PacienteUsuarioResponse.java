package com.avicia.api.features.associacao.paciente.usuario;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteUsuarioResponse {

    private Integer idUsuario;
    private String nomeUsuario;
    private String emailUsuario;
    private String telefoneUsuario;

    private Integer idPaciente;
    private String nomePaciente;
    private String emailPaciente;

    private LocalDateTime dataVinculo;

}
