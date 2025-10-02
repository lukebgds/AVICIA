package com.avicia.api.data.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioRequest {
    
    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private Boolean ativo;
    private Boolean mfaHabilitado;
    private LocalDate dataCriacao;
    private Integer idRole;

}
