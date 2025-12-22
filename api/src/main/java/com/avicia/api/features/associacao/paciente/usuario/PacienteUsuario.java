package com.avicia.api.features.associacao.paciente.usuario;

import java.time.LocalDateTime;

import com.avicia.api.data.serializer.PacienteUsuarioId;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.usuario.Usuario;

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
@Table(schema = "seguranca", name = "paciente_usuario")
@Getter
@Setter
@NoArgsConstructor
public class PacienteUsuario {

    @Id
    @EmbeddedId
    private PacienteUsuarioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPaciente")
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @Column(name = "data_vinculo", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataVinculo = LocalDateTime.now();
}
