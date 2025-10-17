package com.avicia.api.data.model;

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

}
