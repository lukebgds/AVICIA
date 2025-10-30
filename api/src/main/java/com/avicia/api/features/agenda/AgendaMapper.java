package com.avicia.api.features.agenda;

import com.avicia.api.features.agenda.request.AgendaRequest;
import com.avicia.api.features.agenda.response.AgendaResponse;

public class AgendaMapper {

    public static AgendaResponse toResponseDTO(Agenda agenda) {

        if (agenda == null) return null;

        AgendaResponse dto = new AgendaResponse();

        dto.setIdAgenda(agenda.getIdAgenda());
        dto.setIdProfissionalSaude(agenda.getProfissionalSaude() != null 
            ? agenda.getProfissionalSaude().getIdProfissional() : null);
        dto.setIdPaciente(agenda.getPaciente() != null 
            ? agenda.getPaciente().getIdPaciente() : null);
        dto.setDataHorario(agenda.getDataHorario());
        dto.setStatus(agenda.getStatus());

        return dto;
    }

    public static Agenda toEntity(AgendaRequest dto) {
        
        if (dto == null) return null;

        Agenda agenda = new Agenda();
        
        agenda.setDataHorario(dto.getDataHorario());
        agenda.setStatus(dto.getStatus());

        return agenda;
    }

}
