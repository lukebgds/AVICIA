package com.avicia.api.features.agenda.response;

import java.time.LocalDateTime;

import com.avicia.api.data.enumerate.StatusAgenda;

import lombok.Data;

@Data
public class AgendaResponse {

    private Integer idAgenda;
    private Integer idProfissionalSaude;
    private Integer idPaciente;
    private LocalDateTime dataHorario;
    private StatusAgenda status;

}
