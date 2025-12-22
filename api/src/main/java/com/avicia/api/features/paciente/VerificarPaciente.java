package com.avicia.api.features.paciente;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.usuario.Usuario;
import com.avicia.api.features.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPaciente {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID do paciente não é nulo
     */
    public void validarIdPacienteNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do paciente não pode ser nulo");
        }
    }

    /**
     * Valida se o CPF não é nulo ou vazio
     */
    public void validarCpfNaoVazio(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            systemError.error("CPF não pode ser vazio");
        }
    }

    /**
     * Valida se o ID do usuário não é nulo
     */
    public void validarIdUsuarioNaoNulo(Integer idUsuario) {
        if (idUsuario == null) {
            systemError.error("ID do usuário não pode ser nulo");
        }
    }

    /**
     * Busca paciente por ID ou lança exceção
     */
    public Paciente buscarPacientePorId(Integer id) {
        validarIdPacienteNaoNulo(id);
        Paciente paciente = pacienteRepository.findById(id).orElse(null);
        if (paciente == null) {
            systemError.error("Paciente com ID %d não encontrado", id);
        }
        return paciente;
    }

    /**
     * Busca paciente por CPF ou lança exceção
     */
    public Paciente buscarPacientePorCpf(String cpf) {
        validarCpfNaoVazio(cpf);
        Paciente paciente = pacienteRepository.findByUsuario_Cpf(cpf).orElse(null);
        if (paciente == null) {
            systemError.error("Paciente com CPF %s não encontrado", cpf);
        }
        return paciente;
    }

    /**
     * Busca usuário por ID ou lança exceção
     */
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(idUsuario, "Paciente", "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    /**
     * Busca usuário por ID para atualização ou lança exceção
     */
    public Usuario buscarUsuarioPorIdAtualizacao(Integer idUsuario, Integer idPaciente) {
        Usuario usuario = usuarioRepository.findById(idPaciente).orElse(null);
        if (usuario == null) {
            systemError.error(idUsuario, "Paciente", "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    /**
     * Verifica se já existe um paciente para o usuário
     */
    public void verificarPacienteDuplicado(String cpf) {
        if (pacienteRepository.findByUsuario_Cpf(cpf).isPresent()) {
            systemError.error(
                100,
                "Paciente",
                "Já existe um paciente cadastrado para o usuário com CPF %s",
                cpf
            );
        }
    }

    /**
     * Valida se o ID do usuário é válido para geração de ID do paciente
     */
    public void validarIdUsuarioParaGeracao(Integer idUsuario) {
        String idUsuarioStr = String.valueOf(idUsuario);
        
        if (idUsuarioStr.length() < 3) {
            systemError.error("ID do usuário inválido para geração de ID do paciente");
        }
    }

    /**
     * Verifica se o ID do paciente já existe
     */
    public boolean idPacienteExiste(Integer idPaciente) {
        return pacienteRepository.existsById(idPaciente);
    }
}