package com.avicia.api.features.paciente.dados.alergia;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPacienteAlergia {

    private final PacienteAlergiaRepository pacienteAlergiaRepository;
    private final PacienteRepository pacienteRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID da alergia não é nulo
     */
    public void validarIdAlergiaNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID da alergia não pode ser nulo");
        }
    }

    /**
     * Valida se o ID do paciente não é nulo
     */
    public void validarIdPacienteNaoNulo(Integer idPaciente) {
        if (idPaciente == null) {
            systemError.error("ID do paciente não pode ser nulo");
        }
    }

    /**
     * Busca alergia por ID ou lança exceção
     */
    public PacienteAlergia buscarAlergiaPorId(Integer id) {
        validarIdAlergiaNaoNulo(id);
        PacienteAlergia alergia = pacienteAlergiaRepository.findById(id).orElse(null);
        if (alergia == null) {
            systemError.error("Alergia com ID %d não encontrada", id);
        }
        return alergia;
    }

    /**
     * Busca paciente por ID ou lança exceção
     */
    public Paciente buscarPacientePorId(Integer idPaciente) {
        validarIdPacienteNaoNulo(idPaciente);
        Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);
        if (paciente == null) {
            systemError.error("Paciente com ID %d não encontrado", idPaciente);
        }
        return paciente;
    }

    /**
     * Verifica se o ID da alergia já existe
     */
    public boolean idAlergiaExiste(Integer idAlergia) {
        return pacienteAlergiaRepository.existsById(idAlergia);
    }

}
