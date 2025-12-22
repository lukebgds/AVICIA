package com.avicia.api.features.paciente.dados.antecedente;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPacienteAntecedente {

    private final PacienteAntecedenteRepository pacienteAntecedenteRepository;
    private final PacienteRepository pacienteRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID do antecedente não é nulo
     */
    public void validarIdAntecedenteNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do antecedente não pode ser nulo");
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
     * Valida se o tipo de doença não é nulo ou vazio
     */
    public void validarTipoDoencaNaoVazio(String tipoDoenca) {
        if (tipoDoenca == null || tipoDoenca.trim().isEmpty()) {
            systemError.error("Tipo de doença não pode ser vazio");
        }
    }

    /**
     * Busca antecedente por ID ou lança exceção
     */
    public PacienteAntecedente buscarAntecedentePorId(Integer id) {
        validarIdAntecedenteNaoNulo(id);
        PacienteAntecedente antecedente = pacienteAntecedenteRepository.findById(id).orElse(null);
        if (antecedente == null) {
            systemError.error("Antecedente com ID %d não encontrado", id);
        }
        return antecedente;
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
     * Verifica se o ID do antecedente já existe
     */
    public boolean idAntecedenteExiste(Integer idAntecedente) {
        return pacienteAntecedenteRepository.existsById(idAntecedente);
    }

}
