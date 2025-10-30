package com.avicia.api.features.associacao.paciente.funcionario;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.serializer.PacienteFuncionarioId;
import com.avicia.api.exception.BusinessException;
import com.avicia.api.features.funcionario.Funcionario;
import com.avicia.api.features.funcionario.FuncionarioRepository;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;
import com.avicia.api.features.sistema.systemLog.SystemLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteFuncionarioService {

    private final PacienteFuncionarioRepository vinculoRepository;
    private final PacienteRepository pacienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final VerificarPacienteFuncionario verificar;
    private final SystemLogService systemLogService;

    /**
     * Cria um novo vínculo entre paciente e funcionário.
     */
    @Transactional
    public void criarVinculo(Integer idFuncionario, Integer idPaciente) {
        verificar.verificarEntidadesExistem(idFuncionario, idPaciente);
        verificar.verificarDuplicidade(idFuncionario, idPaciente);

        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new BusinessException("Paciente com ID " + idPaciente + " não encontrado"));
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new BusinessException("Funcionário com ID " + idFuncionario + " não encontrado"));

        PacienteFuncionario vinculo = new PacienteFuncionario();
        vinculo.setId(new PacienteFuncionarioId(idFuncionario, idPaciente));
        vinculo.setFuncionario(funcionario);
        vinculo.setPaciente(paciente);
        vinculo.setDataVinculo(LocalDateTime.now());

        vinculoRepository.save(vinculo);

        systemLogService.registrarCriacao(
            idFuncionario,
            "PacienteFuncionario",
            String.format("Funcionário %d vinculado ao paciente %d", idFuncionario, idPaciente)
        );
    }

    /**
     * Remove um vínculo existente entre paciente e funcionário.
     */
    @Transactional
    public void deletarVinculo(Integer idFuncionario, Integer idPaciente) {
        verificar.verificarVinculoExiste(idFuncionario, idPaciente);

        PacienteFuncionarioId id = new PacienteFuncionarioId(idFuncionario, idPaciente);
        vinculoRepository.deleteById(id);

        systemLogService.registrarExclusao(
            idFuncionario,
            "PacienteFuncionario",
            String.format("Vínculo do funcionário %d com o paciente %d foi removido", idFuncionario, idPaciente)
        );
    }

}
