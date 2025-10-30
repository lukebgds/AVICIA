package com.avicia.api.features.associacao.paciente.profissional;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.serializer.PacienteProfissionalSaudeId;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.profissional.ProfissionalSaudeRepository;
import com.avicia.api.features.sistema.systemLog.SystemLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteProfissionalSaudeService {

    private final PacienteProfissionalSaudeRepository vinculoRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final VerificarPacienteProfissionalSaude verificar;
    private final SystemLogService systemLogService;

    /**
     * Cria um novo vínculo paciente-profissional
     */
    @Transactional
    public void criarVinculo(Integer idProfissionalSaude, Integer idPaciente) {
        verificar.verificarEntidadesExistem(idProfissionalSaude, idPaciente);
        verificar.verificarDuplicidade(idProfissionalSaude, idPaciente);

        Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow();
        ProfissionalSaude profissional = profissionalSaudeRepository.findById(idProfissionalSaude).orElseThrow();

        // Mapeamento
        PacienteProfissionalSaude vinculo = new PacienteProfissionalSaude();
        vinculo.setId(new PacienteProfissionalSaudeId(idProfissionalSaude, idPaciente));
        vinculo.setProfissionalSaude(profissional);
        vinculo.setPaciente(paciente);
        vinculo.setDataVinculo(LocalDateTime.now());

        vinculoRepository.save(vinculo);

        systemLogService.registrarCriacao(
            idProfissionalSaude,
            "PacienteProfissionalSaude",
            String.format("Profissional %d vinculado ao paciente %d", idProfissionalSaude, idPaciente)
        );
    }

    /**
     * Remove um vínculo existente
     */
    @Transactional
    public void deletarVinculo(Integer idProfissionalSaude, Integer idPaciente) {
        verificar.verificarVinculoExiste(idProfissionalSaude, idPaciente);

        PacienteProfissionalSaudeId id = new PacienteProfissionalSaudeId(idProfissionalSaude, idPaciente);
        vinculoRepository.deleteById(id);

        systemLogService.registrarExclusao(
            idProfissionalSaude,
            "PacienteProfissionalSaude",
            String.format("Vínculo do profissional %d com o paciente %d foi removido", idProfissionalSaude, idPaciente)
        );
    }

}
