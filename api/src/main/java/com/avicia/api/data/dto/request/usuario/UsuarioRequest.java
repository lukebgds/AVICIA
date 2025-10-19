package com.avicia.api.data.dto.request.usuario;

import java.time.LocalDate;

import lombok.Data;

@Data 
public class UsuarioRequest {
    
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String sexo;
    private String estadoCivil;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    private Boolean ativo;
    private Boolean mfaHabilitado;
    private LocalDate dataCriacao;
    private Integer idRole;

}
