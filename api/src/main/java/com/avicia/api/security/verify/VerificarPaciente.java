package com.avicia.api.security.verify;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.model.Paciente;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.PacienteRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarPaciente {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemError systemError;

    public void validarIdPacienteNaoNulo(Integer id) {
        if (id == null) {
            systemError.error("ID do paciente não pode ser nulo");
        }
    }

    public void validarCpfNaoVazio(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            systemError.error("CPF não pode ser vazio");
        }
    }

    public void validarIdUsuarioNaoNulo(Integer idUsuario) {
        if (idUsuario == null) {
            systemError.error("ID do usuário não pode ser nulo");
        }
    }

    public Paciente buscarPacientePorId(Integer id) {
        validarIdPacienteNaoNulo(id);
        var paciente = pacienteRepository.findById(id).orElse(null);
        if (paciente == null) {
            systemError.error("Paciente com ID %d não encontrado", id);
        }
        return paciente;
    }

    public Paciente buscarPacientePorCpf(String cpf) {
        validarCpfNaoVazio(cpf);
        var paciente = pacienteRepository.findByUsuario_Cpf(cpf).orElse(null);
        if (paciente == null) {
            systemError.error("Paciente com CPF %s não encontrado", cpf);
        }
        return paciente;
    }

    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        var usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(idUsuario, "Paciente", "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    public Usuario buscarUsuarioPorIdAtualizacao(Integer idUsuario, Integer idPaciente) {
        var usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(idPaciente, "Paciente", "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    public void verificarPacienteDuplicado(String cpf) {
        if (pacienteRepository.findByUsuario_Cpf(cpf).isPresent()) {
            systemError.error(100, "Paciente",
                "Já existe um paciente cadastrado para o usuário com CPF %s", cpf);
        }
    }

    public void validarIdUsuarioParaGeracao(Integer idUsuario) {
        String idUsuarioStr = String.valueOf(idUsuario);
        if (idUsuarioStr.length() < 3) {
            systemError.error("ID do usuário inválido para geração de ID do paciente");
        }
    }

    public boolean idPacienteExiste(Integer idPaciente) {
        return pacienteRepository.existsById(idPaciente);
    }
}
