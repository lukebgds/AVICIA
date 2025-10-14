package com.avicia.api.data.dto.response;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.PreferenciaContato;

import lombok.Data;

@Data
public class PacienteResponse {

    private Integer idPaciente;
    private UsuarioResponse usuario;
    private LocalDate dataNascimento;
    private String sexo;
    private String estadoCivil;
    private String profissao;
    private String endereco;
    private String preferenciaContato;

}
