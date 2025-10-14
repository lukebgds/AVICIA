package com.avicia.api.data.dto.object;

import java.time.LocalDate;

import com.avicia.api.model.Role;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Integer idUsuario;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    private String senhaHash;
    private String telefone;
    private Boolean ativo;
    private Boolean mfaHabilitado;
    private LocalDate dataCriacao;
    private Role idRole;

}
