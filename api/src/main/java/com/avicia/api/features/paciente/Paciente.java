package com.avicia.api.features.paciente;

import java.util.Set;

import com.avicia.api.features.associacao.paciente.funcionario.PacienteFuncionario;
import com.avicia.api.features.associacao.paciente.profissional.PacienteProfissionalSaude;
import com.avicia.api.features.associacao.paciente.usuario.PacienteUsuario;
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
@Table(schema = "cadastro", name = "usuario_paciente")
@Getter
@Setter
@NoArgsConstructor
public class Paciente {

    @Id
    @Column(name = "id_paciente")
    private Integer idPaciente;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "profissao")
    private String profissao;

    @Column(name = "preferencia_contato")
    private String preferenciaContato;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PacienteProfissionalSaude> vinculosProfissionalSaude;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PacienteFuncionario> vinculosFuncionario;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PacienteUsuario> vinculosUsuario;

}
