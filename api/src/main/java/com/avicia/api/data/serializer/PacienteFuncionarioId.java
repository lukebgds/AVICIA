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
public class PacienteFuncionarioId {
    private Integer idFuncionario;
    private Integer idPaciente;
}
