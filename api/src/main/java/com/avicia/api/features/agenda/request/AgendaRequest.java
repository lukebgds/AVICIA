package com.avicia.api.features.agenda.request;

import java.time.LocalDateTime;

import com.avicia.api.data.enumerate.StatusAgenda;

import lombok.Data;

@Data
public class AgendaRequest {

    private Integer idProfissionalSaude;
    private Integer idPaciente;
    private LocalDateTime dataHorario;
    private StatusAgenda status;

}
