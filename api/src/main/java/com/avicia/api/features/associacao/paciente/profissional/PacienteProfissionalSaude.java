package com.avicia.api.features.associacao.paciente.profissional;

import java.time.LocalDateTime;

import com.avicia.api.data.serializer.PacienteProfissionalSaudeId;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.profissional.ProfissionalSaude;

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
@Table(schema = "seguranca", name = "paciente_profissional_saude")
@Getter
@Setter
@NoArgsConstructor
public class PacienteProfissionalSaude {

    @Id
    @EmbeddedId
    private PacienteProfissionalSaudeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProfissional")
    @JoinColumn(name = "id_profissional_saude")
    private ProfissionalSaude profissionalSaude;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPaciente")
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @Column(name = "data_vinculo", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataVinculo = LocalDateTime.now();

}
