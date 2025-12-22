package com.avicia.api.features.clinico.internacao;

import java.time.LocalDate;

import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.profissional.ProfissionalSaude;

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
@Table(schema = "clinico", name = "internacao")
@Getter
@Setter
@NoArgsConstructor
public class Internacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_internacao")
    private Integer idInternacao;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_profissional_saude")
    private ProfissionalSaude profissionalSaude;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "data_alta")
    private LocalDate dataAlta;

    @Column(name = "leito")
    private String leito;

    @Column(name = "observacoes")
    private String observacaoes;
}
