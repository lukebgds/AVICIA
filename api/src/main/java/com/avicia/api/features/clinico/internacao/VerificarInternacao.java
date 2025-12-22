package com.avicia.api.features.clinico.internacao;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.profissional.ProfissionalSaudeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarInternacao {

    private final InternacaoRepository internacaoRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final SystemError systemError;

    public void validarIdInternacaoNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID da internação não pode ser nulo");
        }
    }

    public void validarIdPacienteNaoNulo(Integer idPaciente) {
        if (idPaciente == null) {
            systemError.error("ID do paciente não pode ser nulo");
        }
    }

    public void validarIdProfissionalSaudeNaoNulo(Integer idProfissional) {
        if (idProfissional == null) {
            systemError.error("ID do profissional de saúde não pode ser nulo");
        }
    }

    public void validarDataAdmissaoNaoNula(java.time.LocalDate dataAdmissao) {
        if (dataAdmissao == null) {
            systemError.error("Data de admissão não pode ser nula");
        }
    }

    public void validarLeitoNaoVazio(String leito) {
        if (leito == null || leito.trim().isEmpty()) {
            systemError.error("Leito não pode ser vazio");
        }
    }

    public Internacao buscarInternacaoPorId(Integer id) {
        validarIdInternacaoNaoNulo(id);
        Internacao internacao = internacaoRepository.findById(id).orElse(null);
        if (internacao == null) {
            systemError.error("Internação com ID %d não encontrada", id);
        }
        return internacao;
    }

    public Paciente buscarPacientePorId(Integer idPaciente) {
        validarIdPacienteNaoNulo(idPaciente);
        Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);
        if (paciente == null) {
            systemError.error("Paciente com ID %d não encontrado", idPaciente);
        }
        return paciente;
    }

    public ProfissionalSaude buscarProfissionalSaudePorId(Integer idProfissional) {
        validarIdProfissionalSaudeNaoNulo(idProfissional);
        ProfissionalSaude profissional = profissionalSaudeRepository.findById(idProfissional).orElse(null);
        if (profissional == null) {
            systemError.error("Profissional de saúde com ID %d não encontrado", idProfissional);
        }
        return profissional;
    }

}
