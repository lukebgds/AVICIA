package com.avicia.api.features.clinico.consulta;

import java.time.LocalDateTime;

import com.avicia.api.data.enumerate.TipoConsulta;
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
@Table(schema = "clinico", name = "consulta")
@Getter
@Setter
@NoArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_consulta")
    private Integer idConsulta;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_profissional_saude")
    private ProfissionalSaude profissionalSaude;

    @Column(name = "data_consulta")
    private LocalDateTime dataConsulta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoConsulta tipoConsulta;

    @Column(name = "local_consulta")
    private String localConsulta;

    @Column(name = "anamnese")
    private String anamnese;

    @Column(name = "observacoes")
    private String observacoes;
}
