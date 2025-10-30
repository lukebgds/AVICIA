package com.avicia.api.features.paciente.dados.medicamento;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPacienteMedicamento {

    private final PacienteMedicamentoRepository pacienteMedicamentoRepository;
    private final PacienteRepository pacienteRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID do medicamento não é nulo
     */
    public void validarIdMedicamentoNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do medicamento não pode ser nulo");
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
     * Valida se o nome do medicamento não é nulo ou vazio
     */
    public void validarMedicamentoNaoVazio(String medicamento) {
        if (medicamento == null || medicamento.trim().isEmpty()) {
            systemError.error("Nome do medicamento não pode ser vazio");
        }
    }

    /**
     * Valida se a dosagem não é nula ou vazia
     */
    public void validarDosagemNaoVazia(String dosagem) {
        if (dosagem == null || dosagem.trim().isEmpty()) {
            systemError.error("Dosagem do medicamento não pode ser vazia");
        }
    }

    /**
     * Busca medicamento por ID ou lança exceção
     */
    public PacienteMedicamento buscarMedicamentoPorId(Integer id) {
        validarIdMedicamentoNaoNulo(id);
        PacienteMedicamento medicamento = pacienteMedicamentoRepository.findById(id).orElse(null);
        if (medicamento == null) {
            systemError.error("Medicamento com ID %d não encontrado", id);
        }
        return medicamento;
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
     * Verifica se o ID do medicamento já existe
     */
    public boolean idMedicamentoExiste(Integer idMedicamento) {
        return pacienteMedicamentoRepository.existsById(idMedicamento);
    }

}
