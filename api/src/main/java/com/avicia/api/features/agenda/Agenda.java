package com.avicia.api.features.agenda;

import java.time.LocalDateTime;

import com.avicia.api.data.enumerate.StatusAgenda;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.profissional.ProfissionalSaude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(schema = "administrativo", name = "agenda")
@Getter
@Setter
@NoArgsConstructor
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_agenda")
    private Integer idAgenda;

    @ManyToOne
    @JoinColumn(name = "id_profissional_saude")
    private ProfissionalSaude profissionalSaude;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @Column(name = "data_horario")
    private LocalDateTime dataHorario;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAgenda status;
}
