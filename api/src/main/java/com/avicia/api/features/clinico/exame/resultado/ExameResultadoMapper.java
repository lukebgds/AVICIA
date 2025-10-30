package com.avicia.api.features.clinico.exame.resultado;

import com.avicia.api.features.clinico.exame.resultado.request.ExameResultadoRequest;
import com.avicia.api.features.clinico.exame.resultado.response.ExameResultadoResponse;

public class ExameResultadoMapper {

    public static ExameResultadoResponse toResponseDTO(ExameResultado resultado) {

        if (resultado == null) return null;

        ExameResultadoResponse dto = new ExameResultadoResponse();

        dto.setIdResultado(resultado.getIdResultado());
        dto.setIdExameSolicitado(resultado.getExameSolicitado() != null 
            ? resultado.getExameSolicitado().getIdExameSolicitado() : null);
        dto.setNomeExame(resultado.getExameSolicitado() != null 
            && resultado.getExameSolicitado().getExame() != null
            ? resultado.getExameSolicitado().getExame().getNome() : null);
        dto.setDataResultado(resultado.getDataResultado());
        dto.setLaudo(resultado.getLaudo());
        dto.setArquivoResultado(resultado.getArquivoResultado());
        dto.setObservacoes(resultado.getObservacoes());
        dto.setIdAssinadoPor(resultado.getAssinadoPor() != null 
            ? resultado.getAssinadoPor().getIdProfissional() : null);
        dto.setStatus(resultado.getStatus());

        return dto;
    }

    public static ExameResultado toEntity(ExameResultadoRequest dto) {
        
        if (dto == null) return null;

        ExameResultado resultado = new ExameResultado();
        
        resultado.setDataResultado(dto.getDataResultado());
        resultado.setLaudo(dto.getLaudo());
        resultado.setArquivoResultado(dto.getArquivoResultado());
        resultado.setObservacoes(dto.getObservacoes());
        resultado.setStatus(dto.getStatus());

        return resultado;
    }

}
