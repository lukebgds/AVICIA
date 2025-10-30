package com.avicia.api.features.clinico.exame.solicitado;

import java.time.LocalDate;

import com.avicia.api.features.clinico.consulta.Consulta;
import com.avicia.api.features.clinico.exame.Exame;
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
@Table(schema = "clinico", name = "exame_solicitado")
@Getter
@Setter
@NoArgsConstructor
public class ExameSolicitado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_exame_solicitado")
    private Integer idExameSolicitado;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_exame")
    private Exame exame;

    @ManyToOne
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "id_profissional_saude")
    private ProfissionalSaude profissionalSaude;

    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao;

    @Column(name = "observacoes")
    private String observacoes; 

    @Column(name = "status")
    private String status;

}
