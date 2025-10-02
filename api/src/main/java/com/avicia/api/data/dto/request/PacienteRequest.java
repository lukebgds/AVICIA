package com.avicia.api.data.dto.request;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.PreferenciaContato;

import lombok.Data;

@Data
public class PacienteRequest {

    private Integer idUsuario; 
    private LocalDate dataNascimento;
    private String sexo;
    private String estadoCivil;
    private String profissao;
    private String endereco;
    private String preferenciaContato;

}
