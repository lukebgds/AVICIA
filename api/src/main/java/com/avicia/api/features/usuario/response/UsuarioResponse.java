package com.avicia.api.features.usuario.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioResponse {

    private Integer idUsuario;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String sexo;
    private String estadoCivil;
    private String email;
    private String telefone;
    private String endereco;
    private Boolean ativo;
    private Boolean mfaHabilitado;
    private LocalDate dataCriacao;
    private Integer idRole;
}
