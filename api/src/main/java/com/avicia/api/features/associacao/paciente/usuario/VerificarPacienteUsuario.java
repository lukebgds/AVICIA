package com.avicia.api.features.associacao.paciente.usuario;

import org.springframework.stereotype.Component;

import com.avicia.api.data.serializer.PacienteUsuarioId;
import com.avicia.api.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPacienteUsuario {

    private final PacienteUsuarioRepository pacienteUsuarioRepository;

    public void verificarEntidadesExistem(Integer idUsuario, Integer idPaciente) {
        if (idUsuario == null || idPaciente == null) {
            throw new BusinessException("IDs de usuário e paciente são obrigatórios");
        }
    }

    public void verificarDuplicidade(Integer idUsuario, Integer idPaciente) {
        if (pacienteUsuarioRepository.existsById(new PacienteUsuarioId(idUsuario, idPaciente))) {
            throw new BusinessException("Vínculo entre usuário e paciente já existe");
        }
    }

    public void verificarVinculoExiste(Integer idUsuario, Integer idPaciente) {
        if (!pacienteUsuarioRepository.existsById(new PacienteUsuarioId(idUsuario, idPaciente))) {
            throw new BusinessException("Vínculo entre usuário e paciente não encontrado");
        }
    }

}
