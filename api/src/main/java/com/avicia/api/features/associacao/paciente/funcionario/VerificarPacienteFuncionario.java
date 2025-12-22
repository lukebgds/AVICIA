package com.avicia.api.features.associacao.paciente.funcionario;

import org.springframework.stereotype.Component;

import com.avicia.api.data.serializer.PacienteFuncionarioId;
import com.avicia.api.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPacienteFuncionario {

    private final PacienteFuncionarioRepository pacienteFuncionarioRepository;

    public void verificarEntidadesExistem(Integer idFuncionario, Integer idPaciente) {
        if (idFuncionario == null || idPaciente == null) {
            throw new BusinessException("IDs de funcionário e paciente são obrigatórios");
        }
    }

    public void verificarDuplicidade(Integer idFuncionario, Integer idPaciente) {
        if (pacienteFuncionarioRepository.existsById(new PacienteFuncionarioId(idFuncionario, idPaciente))) {
            throw new BusinessException("Vínculo entre funcionário e paciente já existe");
        }
    }

    public void verificarVinculoExiste(Integer idFuncionario, Integer idPaciente) {
        if (!pacienteFuncionarioRepository.existsById(new PacienteFuncionarioId(idFuncionario, idPaciente))) {
            throw new BusinessException("Vínculo entre funcionário e paciente não encontrado");
        }
    }

}
