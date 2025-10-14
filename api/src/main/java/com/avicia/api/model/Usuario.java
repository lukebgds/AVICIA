package com.avicia.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "seguranca", name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private Role idRole;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "sexo", nullable = false)
    private String sexo;

    @Column(name = "estado_civil", nullable = false)
    private String estadoCivil;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telefone", nullable = false, unique = true)
    private String telefone;

    @Column(name = "endereco", nullable = false)
    private String endereco;


    @Column(name = "senha_hash", nullable = false, unique = true)
    private String senhaHash;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @Column(name = "mfa_habilitado", nullable = false)
    private Boolean mfaHabilitado;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;
}
