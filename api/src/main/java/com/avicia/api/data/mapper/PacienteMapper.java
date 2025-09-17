package com.avicia.api.data.mapper;

import com.avicia.api.data.dto.object.PacienteDTO;
import com.avicia.api.model.Paciente;

public class PacienteMapper {

    public static PacienteDTO toDTO(Paciente paciente) {

        if (paciente == null) return null;

        PacienteDTO dto = new PacienteDTO();

        dto.setIdPaciente(paciente.getIdPaciente());
        dto.setUsuario(UsuarioMapper.toDTO(paciente.getUsuario()));
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setSexo(paciente.getSexo());
        dto.setEstadoCivil(paciente.getEstadoCivil());
        dto.setProfissao(paciente.getProfissao());
        dto.setEndereco(paciente.getEndereco());
        dto.setTelefone(paciente.getTelefone());
        dto.setPreferenciaContato(paciente.getPreferenciaContato());

        return dto;
    }

    public static Paciente toEntity(PacienteDTO dto) {
        
        if (dto == null) return null;

        Paciente paciente = new Paciente();

        paciente.setIdPaciente(dto.getIdPaciente());
        paciente.setUsuario(UsuarioMapper.toEntity(dto.getUsuario()));
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setSexo(dto.getSexo());
        paciente.setEstadoCivil(dto.getEstadoCivil());
        paciente.setProfissao(dto.getProfissao());
        paciente.setEndereco(dto.getEndereco());
        paciente.setTelefone(dto.getTelefone());
        paciente.setPreferenciaContato(dto.getPreferenciaContato());
        
        return paciente;
    }

}
