package com.avicia.api.features.associacao.paciente.funcionario;

import java.time.LocalDateTime;

import com.avicia.api.data.serializer.PacienteFuncionarioId;
import com.avicia.api.features.funcionario.Funcionario;
import com.avicia.api.features.paciente.Paciente;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "seguranca", name = "paciente_funcionario")
@Getter
@Setter
@NoArgsConstructor
public class PacienteFuncionario {

    @Id
    @EmbeddedId
    private PacienteFuncionarioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idFuncionario")
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPaciente")
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @Column(name = "data_vinculo", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataVinculo = LocalDateTime.now();


}
