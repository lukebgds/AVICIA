package com.avicia.api.util;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomGetter {

    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;
    private final PacienteRepository pacienteRepository;
    private final SystemError systemError;

    public Integer getIdPacienteByCpf(String cpf) {
        Integer idUsuarioToken = usuarioAutenticadoUtil.getIdUsuario();

        Integer idPaciente = pacienteRepository.findByUsuario_Cpf(cpf)
            .map(Paciente::getIdPaciente)
            .orElse(null);

        if (idPaciente == null) {
            systemError.error(idUsuarioToken, "Paciente", "Não encontrado ID de Paciente para o CPF: %s", cpf);
        }

        return idPaciente;
    }
}
