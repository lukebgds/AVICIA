package com.avicia.api.features.clinico.consulta;

import com.avicia.api.features.clinico.consulta.request.ConsultaRequest;
import com.avicia.api.features.clinico.consulta.response.ConsultaResponse;

public class ConsultaMapper {

    public static ConsultaResponse toResponseDTO(Consulta consulta) {

        if (consulta == null) return null;

        ConsultaResponse dto = new ConsultaResponse();

        dto.setIdConsulta(consulta.getIdConsulta());
        dto.setIdPaciente(consulta.getPaciente() != null 
            ? consulta.getPaciente().getIdPaciente() : null);
        dto.setIdProfissionalSaude(consulta.getProfissionalSaude() != null 
            ? consulta.getProfissionalSaude().getIdProfissional() : null);
        dto.setDataConsulta(consulta.getDataConsulta());
        dto.setTipoConsulta(consulta.getTipoConsulta());
        dto.setLocalConsulta(consulta.getLocalConsulta());
        dto.setAnamnese(consulta.getAnamnese());
        dto.setObservacoes(consulta.getObservacoes());

        return dto;
    }

    public static Consulta toEntity(ConsultaRequest dto) {
        
        if (dto == null) return null;

        Consulta consulta = new Consulta();
        
        consulta.setDataConsulta(dto.getDataConsulta());
        consulta.setTipoConsulta(dto.getTipoConsulta());
        consulta.setLocalConsulta(dto.getLocalConsulta());
        consulta.setAnamnese(dto.getAnamnese());
        consulta.setObservacoes(dto.getObservacoes());

        return consulta;
    }

}
