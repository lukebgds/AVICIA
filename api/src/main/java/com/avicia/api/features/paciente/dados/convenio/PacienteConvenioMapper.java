package com.avicia.api.features.paciente.dados.convenio;

import com.avicia.api.features.paciente.dados.convenio.request.PacienteConvenioRequest;
import com.avicia.api.features.paciente.dados.convenio.response.PacienteConvenioResponse;

public class PacienteConvenioMapper {

    public static PacienteConvenioResponse toResponseDTO(PacienteConvenio convenio) {

        if (convenio == null) return null;

        PacienteConvenioResponse dto = new PacienteConvenioResponse();

        dto.setIdConvenio(convenio.getIdConvenio());
        dto.setIdPaciente(convenio.getPaciente() != null ? convenio.getPaciente().getIdPaciente() : null);
        dto.setNomeConvenio(convenio.getNomeConvenio());
        dto.setNumeroCarteirinha(convenio.getNumeroCarteirinha());
        dto.setValidade(convenio.getValidade());

        return dto;
    }

    public static PacienteConvenio toEntity(PacienteConvenioRequest dto) {
        
        if (dto == null) return null;

        PacienteConvenio convenio = new PacienteConvenio();
        
        convenio.setNomeConvenio(dto.getNomeConvenio());
        convenio.setNumeroCarteirinha(dto.getNumeroCarteirinha());
        convenio.setValidade(dto.getValidade());

        return convenio;
    }

}
