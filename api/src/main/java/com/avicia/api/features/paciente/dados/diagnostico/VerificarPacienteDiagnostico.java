package com.avicia.api.features.paciente.dados.diagnostico;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPacienteDiagnostico {

    private final PacienteDiagnosticoRepository pacienteDiagnosticoRepository;
    private final PacienteRepository pacienteRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID do diagnóstico não é nulo
     */
    public void validarIdDiagnosticoNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do diagnóstico não pode ser nulo");
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
     * Valida se o código CID-10 não é nulo ou vazio
     */
    public void validarCodigoCidDezNaoVazio(String codigoCidDez) {
        if (codigoCidDez == null || codigoCidDez.trim().isEmpty()) {
            systemError.error("Código CID-10 não pode ser vazio");
        }
    }

    /**
     * Valida se a data do diagnóstico não é nula
     */
    public void validarDataDiagnosticoNaoNula(java.time.LocalDate dataDiagnostico) {
        if (dataDiagnostico == null) {
            systemError.error("Data do diagnóstico não pode ser nula");
        }
    }

    /**
     * Valida se o tipo do diagnóstico não é nulo
     */
    public void validarTipoDiagnosticoNaoNulo(com.avicia.api.data.enumerate.TipoDiagnostico tipo) {
        if (tipo == null) {
            systemError.error("Tipo do diagnóstico não pode ser nulo");
        }
    }

    /**
     * Valida se o status do diagnóstico não é nulo
     */
    public void validarStatusDiagnosticoNaoNulo(com.avicia.api.data.enumerate.StatusDiagnostico status) {
        if (status == null) {
            systemError.error("Status do diagnóstico não pode ser nulo");
        }
    }

    /**
     * Busca diagnóstico por ID ou lança exceção
     */
    public PacienteDiagnostico buscarDiagnosticoPorId(Integer id) {
        validarIdDiagnosticoNaoNulo(id);
        PacienteDiagnostico diagnostico = pacienteDiagnosticoRepository.findById(id).orElse(null);
        if (diagnostico == null) {
            systemError.error("Diagnóstico com ID %d não encontrado", id);
        }
        return diagnostico;
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
     * Verifica se o ID do diagnóstico já existe
     */
    public boolean idDiagnosticoExiste(Integer idDiagnostico) {
        return pacienteDiagnosticoRepository.existsById(idDiagnostico);
    }

}
