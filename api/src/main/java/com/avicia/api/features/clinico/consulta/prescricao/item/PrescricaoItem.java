package com.avicia.api.features.clinico.consulta.prescricao.item;

import com.avicia.api.features.clinico.consulta.prescricao.ConsultaPrescricao;

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
@Table(schema = "clinico", name = "prescricao_item")
@Getter
@Setter
@NoArgsConstructor
public class PrescricaoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_item")
    private Integer idItem;

    @ManyToOne
    @JoinColumn(name = "id_prescricao")
    private ConsultaPrescricao consultaPrescricao;

    @Column(name = "medicamento")
    private String medicamento;

    @Column(name = "dosagem")
    private String dosagem;

    @Column(name = "frequencia")
    private String frequencia;

    @Column(name = "duracao")
    private String duracao;
}
