package com.avicia.api.model;

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
@Table(schema = "seguranca", name = "usuario_funcionario")
@Getter
@Setter
@NoArgsConstructor
public class Funcionario {

    @Id
    @Column(name = "id_administrativo")
    private Integer idAdministrativo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "setor")
    private String setor;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "observacoes")
    private String observacoes;

}
