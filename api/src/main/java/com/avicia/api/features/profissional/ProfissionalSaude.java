package com.avicia.api.features.profissional;

import java.util.Set;

import com.avicia.api.features.associacao.paciente.profissional.PacienteProfissionalSaude;
import com.avicia.api.features.usuario.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "cadastro", name = "usuario_profissional_saude")
@Getter
@Setter
@NoArgsConstructor
public class ProfissionalSaude {

    @Id
    @Column(name = "id_profissional_saude")
    private Integer idProfissional;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "conselho")
    private String conselho;

    @Column(name = "registroconselho")
    private String registroConselho;

    @Column(name = "especialidade")
    private String especialidade;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "unidade")
    private String unidade;

    @OneToMany(mappedBy = "profissionalSaude", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PacienteProfissionalSaude> vinculosPaciente;

}
