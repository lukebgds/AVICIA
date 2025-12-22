package com.avicia.api.features.paciente.dados.convenio;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPacienteConvenio {

    private final PacienteConvenioRepository pacienteConvenioRepository;
    private final PacienteRepository pacienteRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID do convênio não é nulo
     */
    public void validarIdConvenioNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do convênio não pode ser nulo");
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
     * Valida se o nome do convênio não é nulo ou vazio
     */
    public void validarNomeConvenioNaoVazio(String nomeConvenio) {
        if (nomeConvenio == null || nomeConvenio.trim().isEmpty()) {
            systemError.error("Nome do convênio não pode ser vazio");
        }
    }

    /**
     * Valida se o número da carteirinha não é nulo ou vazio
     */
    public void validarNumeroCarteirinhaNaoVazio(String numeroCarteirinha) {
        if (numeroCarteirinha == null || numeroCarteirinha.trim().isEmpty()) {
            systemError.error("Número da carteirinha não pode ser vazio");
        }
    }

    /**
     * Busca convênio por ID ou lança exceção
     */
    public PacienteConvenio buscarConvenioPorId(Integer id) {
        validarIdConvenioNaoNulo(id);
        PacienteConvenio convenio = pacienteConvenioRepository.findById(id).orElse(null);
        if (convenio == null) {
            systemError.error("Convênio com ID %d não encontrado", id);
        }
        return convenio;
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
     * Verifica se o ID do convênio já existe
     */
    public boolean idConvenioExiste(Integer idConvenio) {
        return pacienteConvenioRepository.existsById(idConvenio);
    }

    /**
     * Verifica se o número da carteirinha já está cadastrado
     */
    public void verificarCarteirinhaDuplicada(String numeroCarteirinha) {
        if (pacienteConvenioRepository.findByNumeroCarteirinha(numeroCarteirinha).isPresent()) {
            systemError.error(
                100,
                "PacienteConvenio",
                "Já existe um convênio cadastrado com a carteirinha %s",
                numeroCarteirinha
            );
        }
    }

    /**
     * Verifica se o número da carteirinha já está cadastrado para outro convênio
     */
    public void verificarCarteirinhaDuplicadaAtualizacao(String numeroCarteirinha, Integer idConvenioAtual) {
        pacienteConvenioRepository.findByNumeroCarteirinha(numeroCarteirinha).ifPresent(convenio -> {
            if (!convenio.getIdConvenio().equals(idConvenioAtual)) {
                systemError.error(
                    100,
                    "PacienteConvenio",
                    "Já existe um convênio cadastrado com a carteirinha %s",
                    numeroCarteirinha
                );
            }
        });
    }

}
