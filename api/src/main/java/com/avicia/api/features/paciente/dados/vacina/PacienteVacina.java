package com.avicia.api.features.paciente.dados.vacina;

import java.time.LocalDateTime;

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
@Table(schema = "cadastro", name = "paciente_vacina")
@Getter
@Setter
@NoArgsConstructor
public class PacienteVacina {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_paciente_vacina")
    private Integer idVacina;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @Column(name = "vacina")
    private String vacina;
    
    @Column(name = "data_aplicacao")
    private LocalDateTime dataAplicacao;
}
