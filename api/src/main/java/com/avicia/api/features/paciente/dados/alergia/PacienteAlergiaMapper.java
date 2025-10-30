package com.avicia.api.features.paciente.dados.alergia;

import com.avicia.api.features.paciente.dados.alergia.request.PacienteAlergiaRequest;
import com.avicia.api.features.paciente.dados.alergia.response.PacienteAlergiaResponse;

public class PacienteAlergiaMapper {

    public static PacienteAlergiaResponse toResponseDTO(PacienteAlergia alergia) {

        if (alergia == null) return null;

        PacienteAlergiaResponse dto = new PacienteAlergiaResponse();

        dto.setIdAlergia(alergia.getIdAlergia());
        dto.setIdPaciente(alergia.getPaciente() != null ? alergia.getPaciente().getIdPaciente() : null);
        dto.setGravidade(alergia.getGravidade());
        dto.setDescricao(alergia.getDescricao());

        return dto;
    }

    public static PacienteAlergia toEntity(PacienteAlergiaRequest dto) {
        
        if (dto == null) return null;

        PacienteAlergia alergia = new PacienteAlergia();
        
        alergia.setGravidade(dto.getGravidade());
        alergia.setDescricao(dto.getDescricao());

        return alergia;
    }
}
