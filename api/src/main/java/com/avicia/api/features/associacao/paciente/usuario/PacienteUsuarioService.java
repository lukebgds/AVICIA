package com.avicia.api.features.associacao.paciente.usuario;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.serializer.PacienteUsuarioId;
import com.avicia.api.exception.BusinessException;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.PacienteRepository;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.features.usuario.Usuario;
import com.avicia.api.features.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteUsuarioService {

    private final PacienteUsuarioRepository vinculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final VerificarPacienteUsuario verificar;
    private final SystemLogService systemLogService;

    /**
     * Cria um novo vínculo entre paciente e usuário.
     */
    @Transactional
    public void criarVinculo(Integer idUsuario, Integer idPaciente) {
        verificar.verificarEntidadesExistem(idUsuario, idPaciente);
        verificar.verificarDuplicidade(idUsuario, idPaciente);

        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new BusinessException("Paciente com ID " + idPaciente + " não encontrado"));
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessException("Usuário com ID " + idUsuario + " não encontrado"));

        PacienteUsuario vinculo = new PacienteUsuario();
        vinculo.setId(new PacienteUsuarioId(idUsuario, idPaciente));
        vinculo.setUsuario(usuario);
        vinculo.setPaciente(paciente);
        vinculo.setDataVinculo(LocalDateTime.now());

        vinculoRepository.save(vinculo);

        systemLogService.registrarCriacao(
            idUsuario,
            "PacienteUsuario",
            String.format("Usuário %d vinculado ao paciente %d", idUsuario, idPaciente)
        );
    }

    /**
     * Remove um vínculo existente entre paciente e usuário.
     */
    @Transactional
    public void deletarVinculo(Integer idUsuario, Integer idPaciente) {
        verificar.verificarVinculoExiste(idUsuario, idPaciente);

        PacienteUsuarioId id = new PacienteUsuarioId(idUsuario, idPaciente);
        vinculoRepository.deleteById(id);

        systemLogService.registrarExclusao(
            idUsuario,
            "PacienteUsuario",
            String.format("Vínculo do usuário %d com o paciente %d foi removido", idUsuario, idPaciente)
        );
    }

}
