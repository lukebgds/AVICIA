package com.avicia.api.features.clinico.consulta;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.profissional.ProfissionalSaudeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarConsulta {

    private final ConsultaRepository consultaRepository;
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final PacienteRepository pacienteRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID da consulta não é nulo
     */
    public void validarIdConsultaNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID da consulta não pode ser nulo");
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
     * Valida se a data da consulta não é nula
     */
    public void validarDataConsultaNaoNula(java.time.LocalDateTime dataConsulta) {
        if (dataConsulta == null) {
            systemError.error("Data da consulta não pode ser nula");
        }
    }

    /**
     * Valida se o tipo da consulta não é nulo
     */
    public void validarTipoConsultaNaoNulo(com.avicia.api.data.enumerate.TipoConsulta tipoConsulta) {
        if (tipoConsulta == null) {
            systemError.error("Tipo da consulta não pode ser nulo");
        }
    }

    /**
     * Busca consulta por ID ou lança exceção
     */
    public Consulta buscarConsultaPorId(Integer id) {
        validarIdConsultaNaoNulo(id);
        Consulta consulta = consultaRepository.findById(id).orElse(null);
        if (consulta == null) {
            systemError.error("Consulta com ID %d não encontrada", id);
        }
        return consulta;
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
     * Verifica se o ID da consulta já existe
     */
    public boolean idConsultaExiste(Integer idConsulta) {
        return consultaRepository.existsById(idConsulta);
    }

}
