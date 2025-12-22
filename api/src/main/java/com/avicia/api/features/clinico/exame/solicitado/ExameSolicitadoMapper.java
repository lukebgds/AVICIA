package com.avicia.api.features.clinico.exame.solicitado;

import com.avicia.api.features.clinico.exame.solicitado.request.ExameSolicitadoRequest;
import com.avicia.api.features.clinico.exame.solicitado.response.ExameSolicitadoResponse;

public class ExameSolicitadoMapper {

    public static ExameSolicitadoResponse toResponseDTO(ExameSolicitado exameSolicitado) {

        if (exameSolicitado == null) return null;

        ExameSolicitadoResponse dto = new ExameSolicitadoResponse();

        dto.setIdExameSolicitado(exameSolicitado.getIdExameSolicitado());
        dto.setIdConsulta(exameSolicitado.getConsulta() != null 
            ? exameSolicitado.getConsulta().getIdConsulta() : null);
        dto.setIdPaciente(exameSolicitado.getPaciente() != null 
            ? exameSolicitado.getPaciente().getIdPaciente() : null);
        dto.setIdExame(exameSolicitado.getExame() != null 
            ? exameSolicitado.getExame().getIdExame() : null);
        dto.setNomeExame(exameSolicitado.getExame() != null 
            ? exameSolicitado.getExame().getNome() : null);
        dto.setIdProfissionalSaude(exameSolicitado.getProfissionalSaude() != null 
            ? exameSolicitado.getProfissionalSaude().getIdProfissional() : null);
        dto.setDataSolicitacao(exameSolicitado.getDataSolicitacao());
        dto.setObservacoes(exameSolicitado.getObservacoes());
        dto.setStatus(exameSolicitado.getStatus());

        return dto;
    }

    public static ExameSolicitado toEntity(ExameSolicitadoRequest dto) {
        
        if (dto == null) return null;

        ExameSolicitado exameSolicitado = new ExameSolicitado();
        
        exameSolicitado.setDataSolicitacao(dto.getDataSolicitacao());
        exameSolicitado.setObservacoes(dto.getObservacoes());
        exameSolicitado.setStatus(dto.getStatus());

        return exameSolicitado;
    }

}
