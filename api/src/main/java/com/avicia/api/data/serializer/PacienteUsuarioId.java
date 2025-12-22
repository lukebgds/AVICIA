package com.avicia.api.data.serializer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteUsuarioId {
    private Integer idUsuario;
    private Integer idPaciente;
}
