package com.avicia.api.features.paciente.dados.diagnostico;

import com.avicia.api.features.paciente.dados.diagnostico.response.PacienteDiagnosticoResponse;
import com.avicia.api.features.paciente.dados.diagnostico.resquest.PacienteDiagnosticoRequest;

public class PacienteDiagnosticoMapper {

    public static PacienteDiagnosticoResponse toResponseDTO(PacienteDiagnostico diagnostico) {

        if (diagnostico == null) return null;

        PacienteDiagnosticoResponse dto = new PacienteDiagnosticoResponse();

        dto.setIdDiagnostico(diagnostico.getIdDiagnostico());
        dto.setIdPaciente(diagnostico.getPaciente() != null ? diagnostico.getPaciente().getIdPaciente() : null);
        dto.setCodigoCidDez(diagnostico.getCodigoCidDez());
        dto.setDescricao(diagnostico.getDescricao());
        dto.setDataDiagnostico(diagnostico.getDataDiagnostico());
        dto.setTipo(diagnostico.getTipo());
        dto.setStatus(diagnostico.getStatus());

        return dto;
    }

    public static PacienteDiagnostico toEntity(PacienteDiagnosticoRequest dto) {
        
        if (dto == null) return null;

        PacienteDiagnostico diagnostico = new PacienteDiagnostico();
        
        diagnostico.setCodigoCidDez(dto.getCodigoCidDez());
        diagnostico.setDescricao(dto.getDescricao());
        diagnostico.setDataDiagnostico(dto.getDataDiagnostico());
        diagnostico.setTipo(dto.getTipo());
        diagnostico.setStatus(dto.getStatus());

        return diagnostico;
    }

}
