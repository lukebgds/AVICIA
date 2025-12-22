package com.avicia.api.features.paciente.dados.vacina;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPacienteVacina {

    private final PacienteVacinaRepository pacienteVacinaRepository;
    private final PacienteRepository pacienteRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID da vacina não é nulo
     */
    public void validarIdVacinaNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID da vacina não pode ser nulo");
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
     * Valida se o nome da vacina não é nulo ou vazio
     */
    public void validarVacinaNaoVazia(String vacina) {
        if (vacina == null || vacina.trim().isEmpty()) {
            systemError.error("Nome da vacina não pode ser vazio");
        }
    }

    /**
     * Valida se a data de aplicação não é nula
     */
    public void validarDataAplicacaoNaoNula(java.time.LocalDateTime dataAplicacao) {
        if (dataAplicacao == null) {
            systemError.error("Data de aplicação da vacina não pode ser nula");
        }
    }

    /**
     * Busca vacina por ID ou lança exceção
     */
    public PacienteVacina buscarVacinaPorId(Integer id) {
        validarIdVacinaNaoNulo(id);
        PacienteVacina vacina = pacienteVacinaRepository.findById(id).orElse(null);
        if (vacina == null) {
            systemError.error("Vacina com ID %d não encontrada", id);
        }
        return vacina;
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
     * Verifica se o ID da vacina já existe
     */
    public boolean idVacinaExiste(Integer idVacina) {
        return pacienteVacinaRepository.existsById(idVacina);
    }

}
