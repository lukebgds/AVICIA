package com.avicia.api.features.clinico.exame.resultado;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.clinico.exame.solicitado.ExameSolicitado;
import com.avicia.api.features.clinico.exame.solicitado.ExameSolicitadoRepository;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.profissional.ProfissionalSaudeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarExameResultado {

    private final ExameResultadoRepository exameResultadoRepository;
    private final ExameSolicitadoRepository exameSolicitadoRepository;
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final SystemError systemError;

    public void validarIdResultadoNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do resultado não pode ser nulo");
        }
    }

    public void validarIdExameSolicitadoNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do exame solicitado não pode ser nulo");
        }
    }

    public void validarDataResultadoNaoNula(java.time.LocalDate data) {
        if (data == null) {
            systemError.error("Data do resultado não pode ser nula");
        }
    }

    public void validarStatusNaoVazio(String status) {
        if (status == null || status.trim().isEmpty()) {
            systemError.error("Status não pode ser vazio");
        }
    }

    public ExameResultado buscarResultadoPorId(Integer id) {
        validarIdResultadoNaoNulo(id);
        ExameResultado resultado = exameResultadoRepository.findById(id).orElse(null);
        if (resultado == null) {
            systemError.error("Resultado com ID %d não encontrado", id);
        }
        return resultado;
    }

    public ExameSolicitado buscarExameSolicitadoPorId(Integer id) {
        validarIdExameSolicitadoNaoNulo(id);
        ExameSolicitado exame = exameSolicitadoRepository.findById(id).orElse(null);
        if (exame == null) {
            systemError.error("Exame solicitado com ID %d não encontrado", id);
        }
        return exame;
    }

    public ProfissionalSaude buscarProfissionalPorId(Integer id) {
        if (id == null) return null; // Opcional
        ProfissionalSaude prof = profissionalSaudeRepository.findById(id).orElse(null);
        if (prof == null) {
            systemError.error("Profissional com ID %d não encontrado", id);
        }
        return prof;
    }

}
