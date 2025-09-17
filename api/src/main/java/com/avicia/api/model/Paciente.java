package com.avicia.api.model;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.PreferenciaContato;
import com.avicia.api.data.enumerate.Sexo;

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
@Table(schema = "seguranca", name = "usuario_paciente")
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

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "sexo")
    private Sexo sexo;

    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "profissao")
    private String profissao;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "preferenciaContato")
    private  PreferenciaContato preferenciaContato;

}
