package com.avicia.api.features.paciente.dados.diagnostico;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.StatusDiagnostico;
import com.avicia.api.data.enumerate.TipoDiagnostico;
import com.avicia.api.features.paciente.Paciente;

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
@Table(schema = "cadastro", name = "paciente_diagnostico")
@Getter
@Setter
@NoArgsConstructor
public class PacienteDiagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_paciente_diagnostico")
    private Integer idDiagnostico;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @Column(name = "codigo_cid10")
    private String codigoCidDez;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_diagnostico")
    private LocalDate dataDiagnostico;

    @Enumerated(EnumType.STRING)
    @Column(name =  "tipo")
    private TipoDiagnostico tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusDiagnostico status;
}
