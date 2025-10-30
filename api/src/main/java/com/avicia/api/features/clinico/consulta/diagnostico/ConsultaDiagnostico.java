package com.avicia.api.features.clinico.consulta.diagnostico;

import com.avicia.api.features.clinico.consulta.Consulta;

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
@Table(schema = "clinico", name = "diagnostico_consulta")
@Getter
@Setter
@NoArgsConstructor
public class ConsultaDiagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_diagnostico_consulta")
    private Integer idDiagnostico;

    @ManyToOne
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    @Column(name = "codigo_cid10")
    private String codigoCidDez;

    @Column(name = "descricao")
    private String descricao;
}
