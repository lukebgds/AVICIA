package com.avicia.api.features.paciente.dados.anexo;

import com.avicia.api.features.paciente.dados.anexo.request.PacienteAnexoRequest;
import com.avicia.api.features.paciente.dados.anexo.response.PacienteAnexoResponse;

public class PacienteAnexoMapper {

    public static PacienteAnexoResponse toResponseDTO(PacienteAnexo anexo) {

        if (anexo == null) return null;

        PacienteAnexoResponse dto = new PacienteAnexoResponse();

        dto.setIdAnexo(anexo.getIdAnexo());
        dto.setIdPaciente(anexo.getPaciente() != null ? anexo.getPaciente().getIdPaciente() : null);
        dto.setTipo(anexo.getTipo());
        dto.setDescricao(anexo.getDescricao());
        dto.setUrlArquivo(anexo.getUrlArquivo());
        dto.setDataUpload(anexo.getDataUpload());

        return dto;
    }

    public static PacienteAnexo toEntity(PacienteAnexoRequest dto) {
        
        if (dto == null) return null;

        PacienteAnexo anexo = new PacienteAnexo();
        
        anexo.setTipo(dto.getTipo());
        anexo.setDescricao(dto.getDescricao());
        anexo.setUrlArquivo(dto.getUrlArquivo());

        return anexo;
    }

}
