package com.avicia.api.features.clinico.internacao;

import com.avicia.api.features.clinico.internacao.request.InternacaoRequest;
import com.avicia.api.features.clinico.internacao.response.InternacaoResponse;

public class InternacaoMapper {

    public static InternacaoResponse toResponseDTO(Internacao internacao) {

        if (internacao == null) return null;

        InternacaoResponse dto = new InternacaoResponse();

        dto.setIdInternacao(internacao.getIdInternacao());
        dto.setIdPaciente(internacao.getPaciente() != null 
            ? internacao.getPaciente().getIdPaciente() : null);
        dto.setIdProfissionalSaude(internacao.getProfissionalSaude() != null 
            ? internacao.getProfissionalSaude().getIdProfissional() : null);
        dto.setDataAdmissao(internacao.getDataAdmissao());
        dto.setDataAlta(internacao.getDataAlta());
        dto.setLeito(internacao.getLeito());
        dto.setObservacoes(internacao.getObservacaoes());

        return dto;
    }

    public static Internacao toEntity(InternacaoRequest dto) {
        
        if (dto == null) return null;

        Internacao internacao = new Internacao();
        
        internacao.setDataAdmissao(dto.getDataAdmissao());
        internacao.setDataAlta(dto.getDataAlta());
        internacao.setLeito(dto.getLeito());
        internacao.setObservacaoes(dto.getObservacoes());

        return internacao;
    }

}
