package com.avicia.api.features.agenda;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.profissional.ProfissionalSaudeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarAgenda {

    private final AgendaRepository agendaRepository;
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final PacienteRepository pacienteRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID da agenda não é nulo
     */
    public void validarIdAgendaNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID da agenda não pode ser nulo");
        }
    }

    /**
     * Valida se o ID do profissional de saúde não é nulo
     */
    public void validarIdProfissionalSaudeNaoNulo(Integer idProfissionalSaude) {
        if (idProfissionalSaude == null) {
            systemError.error("ID do profissional de saúde não pode ser nulo");
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
     * Valida se a data e horário não são nulos
     */
    public void validarDataHorarioNaoNulo(java.time.LocalDateTime dataHorario) {
        if (dataHorario == null) {
            systemError.error("Data e horário da agenda não podem ser nulos");
        }
    }

    /**
     * Valida se o status não é nulo
     */
    public void validarStatusNaoNulo(com.avicia.api.data.enumerate.StatusAgenda status) {
        if (status == null) {
            systemError.error("Status da agenda não pode ser nulo");
        }
    }

    /**
     * Busca agenda por ID ou lança exceção
     */
    public Agenda buscarAgendaPorId(Integer id) {
        validarIdAgendaNaoNulo(id);
        Agenda agenda = agendaRepository.findById(id).orElse(null);
        if (agenda == null) {
            systemError.error("Agenda com ID %d não encontrada", id);
        }
        return agenda;
    }

    /**
     * Busca profissional de saúde por ID ou lança exceção
     */
    public ProfissionalSaude buscarProfissionalSaudePorId(Integer idProfissionalSaude) {
        validarIdProfissionalSaudeNaoNulo(idProfissionalSaude);
        ProfissionalSaude profissional = profissionalSaudeRepository.findById(idProfissionalSaude).orElse(null);
        if (profissional == null) {
            systemError.error("Profissional de saúde com ID %d não encontrado", idProfissionalSaude);
        }
        return profissional;
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
     * Verifica se o ID da agenda já existe
     */
    public boolean idAgendaExiste(Integer idAgenda) {
        return agendaRepository.existsById(idAgenda);
    }

}
