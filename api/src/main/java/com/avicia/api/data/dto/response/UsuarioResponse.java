package com.avicia.api.data.dto.response;

import java.time.LocalDate;

import com.avicia.api.data.dto.object.RoleDTO;

import lombok.Data;

@Data
public class UsuarioResponse {

    private Integer idUsuario;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    private String telefone;
    private Boolean ativo;
    private Boolean mfaHabilitado;
    private LocalDate dataCriacao;
    private Integer idRole;

}
