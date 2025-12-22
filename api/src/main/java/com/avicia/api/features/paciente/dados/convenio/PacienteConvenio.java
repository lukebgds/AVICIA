package com.avicia.api.features.paciente.dados.convenio;

import java.time.LocalDate;

import com.avicia.api.features.paciente.Paciente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "cadastro", name = "paciente_convenio")
@Getter
@Setter
@NoArgsConstructor
public class PacienteConvenio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_paciente_convenio")
    private Integer idConvenio;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @Column(name = "nome_convenio")
    private String nomeConvenio;

    @Column(name = "numero_carteirinha")
    private String numeroCarteirinha;

    @Column(name = "validade")
    private LocalDate validade;

}
