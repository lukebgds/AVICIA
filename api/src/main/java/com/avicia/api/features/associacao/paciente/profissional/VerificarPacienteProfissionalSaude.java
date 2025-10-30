package com.avicia.api.features.associacao.paciente.profissional;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.paciente.PacienteRepository;
import com.avicia.api.features.profissional.ProfissionalSaudeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPacienteProfissionalSaude {

    private final PacienteRepository pacienteRepository;
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final PacienteProfissionalSaudeRepository vinculoRepository;
    private final SystemError systemError;

    /**
     * Verifica se o paciente e o profissional existem
     */
    public void verificarEntidadesExistem(Integer idProfissionalSaude, Integer idPaciente) {
        if (!profissionalSaudeRepository.existsById(idProfissionalSaude)) {
            systemError.error(idProfissionalSaude, "ProfissionalSaude",
                "Profissional de saúde com ID %d não encontrado", idProfissionalSaude);
        }
        if (!pacienteRepository.existsById(idPaciente)) {
            systemError.error(idPaciente, "Paciente",
                "Paciente com ID %d não encontrado", idPaciente);
        }
    }

    /**
     * Verifica se o vínculo já existe
     */
    public void verificarDuplicidade(Integer idProfissionalSaude, Integer idPaciente) {
        if (vinculoRepository.existsById_IdProfissionalAndId_IdPaciente(idProfissionalSaude, idPaciente)) {
            systemError.error(idProfissionalSaude, "PacienteProfissionalSaude",
                "O profissional já possui vínculo com o paciente informado");
        }
    }

    /**
     * Verifica se o vínculo existe antes de deletar
     */
    public void verificarVinculoExiste(Integer idProfissionalSaude, Integer idPaciente) {
        if (!vinculoRepository.existsById_IdProfissionalAndId_IdPaciente(idProfissionalSaude, idPaciente)) {
            systemError.error(idProfissionalSaude, "PacienteProfissionalSaude",
                "Vínculo entre profissional e paciente não encontrado");
        }
    }

}
