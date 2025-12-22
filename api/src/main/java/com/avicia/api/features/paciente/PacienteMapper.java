
package com.avicia.api.features.paciente;

import com.avicia.api.features.paciente.request.PacienteRequest;
import com.avicia.api.features.paciente.response.PacienteResponse;
import com.avicia.api.features.usuario.UsuarioMapper;

public class PacienteMapper {

    public static PacienteResponse toResponseDTO(Paciente paciente) {

        if (paciente == null) return null;

        PacienteResponse dto = new PacienteResponse();

        dto.setIdPaciente(paciente.getIdPaciente());
        dto.setUsuario(UsuarioMapper.toResponseDTO(paciente.getUsuario()));
        dto.setProfissao(paciente.getProfissao());
        dto.setPreferenciaContato(paciente.getPreferenciaContato());

        return dto;
    }

    public static Paciente toEntity(PacienteRequest dto) {
        
        if (dto == null) return null;

        Paciente paciente = new Paciente();
        
        paciente.setProfissao(dto.getProfissao());
        paciente.setPreferenciaContato(dto.getPreferenciaContato());

        return paciente;
    }

}
