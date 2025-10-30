package com.avicia.api.features.paciente.dados.antecedente;

import com.avicia.api.features.paciente.dados.antecedente.request.PacienteAntecedenteRequest;
import com.avicia.api.features.paciente.dados.antecedente.response.PacienteAntecedenteResponse;

public class PacienteAntecedenteMapper {

    public static PacienteAntecedenteResponse toResponseDTO(PacienteAntecedente antecedente) {

        if (antecedente == null) return null;

        PacienteAntecedenteResponse dto = new PacienteAntecedenteResponse();

        dto.setIdAntecedente(antecedente.getIdAntecedente());
        dto.setIdPaciente(antecedente.getPaciente() != null ? antecedente.getPaciente().getIdPaciente() : null);
        dto.setTipoDoenca(antecedente.getTipoDoenca());
        dto.setParentesco(antecedente.getParentesco());

        return dto;
    }

    public static PacienteAntecedente toEntity(PacienteAntecedenteRequest dto) {
        
        if (dto == null) return null;

        PacienteAntecedente antecedente = new PacienteAntecedente();
        
        antecedente.setTipoDoenca(dto.getTipoDoenca());
        antecedente.setParentesco(dto.getParentesco());

        return antecedente;
    }

}
