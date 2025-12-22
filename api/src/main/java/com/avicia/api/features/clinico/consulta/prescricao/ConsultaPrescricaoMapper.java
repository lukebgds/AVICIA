package com.avicia.api.features.clinico.consulta.prescricao;

import com.avicia.api.features.clinico.consulta.prescricao.request.ConsultaPrescricaoRequest;
import com.avicia.api.features.clinico.consulta.prescricao.response.ConsultaPrescricaoResponse;

public class ConsultaPrescricaoMapper {

    public static ConsultaPrescricaoResponse toResponseDTO(ConsultaPrescricao prescricao) {

        if (prescricao == null) return null;

        ConsultaPrescricaoResponse dto = new ConsultaPrescricaoResponse();

        dto.setIdPrescricao(prescricao.getIdPrescricao());
        dto.setIdConsulta(prescricao.getConsulta() != null 
            ? prescricao.getConsulta().getIdConsulta() : null);
        dto.setDataEmissao(prescricao.getDataEmissao());
        dto.setObservacoes(prescricao.getObservacoes());
        dto.setStatus(prescricao.getStatus());

        return dto;
    }

    public static ConsultaPrescricao toEntity(ConsultaPrescricaoRequest dto) {
        
        if (dto == null) return null;

        ConsultaPrescricao prescricao = new ConsultaPrescricao();
        
        prescricao.setDataEmissao(dto.getDataEmissao());
        prescricao.setObservacoes(dto.getObservacoes());
        prescricao.setStatus(dto.getStatus());

        return prescricao;
    }

}
