package com.avicia.api.security.verify;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.model.Funcionario;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.FuncionarioRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarFuncionario {

    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemError systemError;

    public void validarIdUsuarioNaoNulo(Integer idUsuario) {
        if (idUsuario == null) {
            systemError.error("ID do usuário não pode ser nulo");
        }
    }

    public void validarMatriculaNaoVazia(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            systemError.error("Matrícula não pode ser vazia");
        }
    }

    public void validarIdFuncionarioNaoNulo(Integer idFuncionario) {
        if (idFuncionario == null) {
            systemError.error("ID do funcionário não pode ser nulo");
        }
    }

    public void verificarMatriculaDuplicada(String matricula) {
        if (funcionarioRepository.findByMatricula(matricula).isPresent()) {
            systemError.error(100, "Funcionario",
                "Já existe um funcionário cadastrado com a matrícula %s", matricula);
        }
    }

    public void verificarMatriculaDuplicadaAtualizacao(String matriculaAtual, String matriculaNova, Integer idFuncionario) {
        if (!matriculaNova.equals(matriculaAtual) &&
            funcionarioRepository.findByMatricula(matriculaNova).isPresent()) {
            systemError.error(idFuncionario, "Funcionario",
                "Já existe um funcionário cadastrado com a matrícula %s", matriculaNova);
        }
    }

    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        var usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(100, "Funcionario", "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    public Usuario buscarUsuarioPorIdAtualizacao(Integer idUsuario, Integer idFuncionario) {
        var usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(idFuncionario, "Funcionario", "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    public Funcionario buscarFuncionarioPorId(Integer idFuncionario) {
        validarIdFuncionarioNaoNulo(idFuncionario);
        var funcionario = funcionarioRepository.findById(idFuncionario).orElse(null);
        if (funcionario == null) {
            systemError.error("Funcionário com ID %d não encontrado", idFuncionario);
        }
        return funcionario;
    }

    public Funcionario buscarFuncionarioPorMatricula(String matricula) {
        validarMatriculaNaoVazia(matricula);
        var funcionario = funcionarioRepository.findByMatricula(matricula).orElse(null);
        if (funcionario == null) {
            systemError.error("Funcionário com matrícula %s não encontrado", matricula);
        }
        return funcionario;
    }

    public void validarIdUsuarioParaGeracao(Integer idUsuario) {
        String idUsuarioStr = String.valueOf(idUsuario);
        if (idUsuarioStr.length() < 3) {
            systemError.error("ID do usuário inválido para geração de ID do funcionário");
        }
    }

    public boolean idFuncionarioExiste(Integer idFuncionario) {
        return funcionarioRepository.existsById(idFuncionario);
    }
}
