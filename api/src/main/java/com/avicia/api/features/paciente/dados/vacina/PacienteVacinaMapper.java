package com.avicia.api.features.paciente.dados.vacina;

import com.avicia.api.features.paciente.dados.vacina.request.PacienteVacinaRequest;
import com.avicia.api.features.paciente.dados.vacina.response.PacienteVacinaResponse;

public class PacienteVacinaMapper {

    public static PacienteVacinaResponse toResponseDTO(PacienteVacina vacina) {

        if (vacina == null) return null;

        PacienteVacinaResponse dto = new PacienteVacinaResponse();

        dto.setIdVacina(vacina.getIdVacina());
        dto.setIdPaciente(vacina.getPaciente() != null ? vacina.getPaciente().getIdPaciente() : null);
        dto.setVacina(vacina.getVacina());
        dto.setDataAplicacao(vacina.getDataAplicacao());

        return dto;
    }

    public static PacienteVacina toEntity(PacienteVacinaRequest dto) {
        
        if (dto == null) return null;

        PacienteVacina vacina = new PacienteVacina();
        
        vacina.setVacina(dto.getVacina());
        vacina.setDataAplicacao(dto.getDataAplicacao());

        return vacina;
    }

}
