package com.avicia.api.features.paciente.dados.antecedente;

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
@Table(schema = "cadastro", name = "paciente_antecedente")
@Getter
@Setter
@NoArgsConstructor
public class PacienteAntecedente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_paciente_antecedente")
    private Integer idAntecedente;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @Column(name = "tipo_doenca")
    private String tipoDoenca;

    @Column(name = "parentesco")
    private String parentesco;
}
