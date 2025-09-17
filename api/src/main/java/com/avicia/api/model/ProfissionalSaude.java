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
@Table(schema = "seguranca", name = "usuario_profissional_saude")
@Getter
@Setter
@NoArgsConstructor
public class ProfissionalSaude {

    @Id
    @Column(name = "id_profissional")
    private Integer idProfissional;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "registro_conselho")
    private String registroConselho;

    @Column(name = "especialidade")
    private String especialidade;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "unidade")
    private String unidade;

}
