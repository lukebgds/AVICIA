package com.avicia.api.features.clinico.consulta.prescricao;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.StatusPrescricao;
import com.avicia.api.features.clinico.consulta.Consulta;

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
@Table(schema = "clinico", name = "prescricao_consulta")
@Getter
@Setter
@NoArgsConstructor
public class ConsultaPrescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_prescricao")
    private Integer idPrescricao;

    @ManyToOne
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    @Column(name = "observacoes")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPrescricao status;
}
