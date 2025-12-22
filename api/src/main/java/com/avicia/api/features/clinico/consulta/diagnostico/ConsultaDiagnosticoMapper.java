package com.avicia.api.features.clinico.consulta.diagnostico;

import com.avicia.api.features.clinico.consulta.diagnostico.request.ConsultaDiagnosticoRequest;
import com.avicia.api.features.clinico.consulta.diagnostico.response.ConsultaDiagnosticoResponse;

public class ConsultaDiagnosticoMapper {

    public static ConsultaDiagnosticoResponse toResponseDTO(ConsultaDiagnostico diagnostico) {

        if (diagnostico == null) return null;

        ConsultaDiagnosticoResponse dto = new ConsultaDiagnosticoResponse();

        dto.setIdDiagnostico(diagnostico.getIdDiagnostico());
        dto.setIdConsulta(diagnostico.getConsulta() != null 
            ? diagnostico.getConsulta().getIdConsulta() : null);
        dto.setCodigoCidDez(diagnostico.getCodigoCidDez());
        dto.setDescricao(diagnostico.getDescricao());

        return dto;
    }

    public static ConsultaDiagnostico toEntity(ConsultaDiagnosticoRequest dto) {
        
        if (dto == null) return null;

        ConsultaDiagnostico diagnostico = new ConsultaDiagnostico();
        
        diagnostico.setCodigoCidDez(dto.getCodigoCidDez());
        diagnostico.setDescricao(dto.getDescricao());

        return diagnostico;
    }

}
