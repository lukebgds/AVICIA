package com.avicia.api.model;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.PreferenciaContato;
import com.avicia.api.data.enumerate.Sexo;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "profissao")
    private String profissao;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "preferencia_contato")
    private String preferenciaContato;

    


}
