package com.avicia.api.data.mapper;

import com.avicia.api.data.dto.request.PacienteRequest;
import com.avicia.api.data.dto.response.PacienteResponse;
import com.avicia.api.model.Paciente;

public class PacienteMapper {

    public static PacienteResponse toResponseDTO(Paciente paciente) {

        if (paciente == null) return null;

        PacienteResponse dto = new PacienteResponse();

        dto.setIdPaciente(paciente.getIdPaciente());
        dto.setUsuario(UsuarioMapper.toResponseDTO(paciente.getUsuario()));
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setSexo(paciente.getSexo());
        dto.setEstadoCivil(paciente.getEstadoCivil());
        dto.setProfissao(paciente.getProfissao());
        dto.setEndereco(paciente.getEndereco());
        dto.setPreferenciaContato(paciente.getPreferenciaContato());

        return dto;
    }

    public static Paciente toEntity(PacienteRequest dto) {
        
        if (dto == null) return null;

        Paciente paciente = new Paciente();
        
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setSexo(dto.getSexo());
        paciente.setEstadoCivil(dto.getEstadoCivil());
        paciente.setProfissao(dto.getProfissao());
        paciente.setEndereco(dto.getEndereco());
        paciente.setPreferenciaContato(dto.getPreferenciaContato());

        return paciente;
    }

}
