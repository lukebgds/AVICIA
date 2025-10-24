package com.avicia.api.security.verify;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.model.Funcionario;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.FuncionarioRepository;
import com.avicia.api.repository.UsuarioRepository;
import com.avicia.api.util.Throwing;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarFuncionario {

    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemError systemError;

    /**
     * Valida se o ID do usuário não é nulo
     */
    public void validarIdUsuarioNaoNulo(Integer idUsuario) {
        if (idUsuario == null) {
            systemError.error("ID do usuário não pode ser nulo");
        }
    }

    /**
     * Valida se a matrícula não é nula ou vazia
     */
    public void validarMatriculaNaoVazia(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            systemError.error("Matrícula não pode ser vazia");
        }
    }

    /**
     * Valida se o ID do funcionário não é nulo
     */
    public void validarIdFuncionarioNaoNulo(Integer idFuncionario) {
        if (idFuncionario == null) {
            systemError.error("ID do funcionário não pode ser nulo");
        }
    }

    /**
     * Verifica se a matrícula já existe
     */
    public void verificarMatriculaDuplicada(String matricula) {
        if (funcionarioRepository.findByMatricula(matricula).isPresent()) {
            systemError.error(
                100,
                "Funcionario",
                "Já existe um funcionário cadastrado com a matrícula %s",
                matricula
            );
        }
    }

    /**
     * Verifica se a nova matrícula já existe (exceto para o próprio funcionário)
     */
    public void verificarMatriculaDuplicadaAtualizacao(String matriculaAtual, String matriculaNova, Integer idFuncionario) {
        if (!matriculaNova.equals(matriculaAtual) && funcionarioRepository.findByMatricula(matriculaNova).isPresent()) {
            systemError.error(
                idFuncionario,
                "Funcionario",
                "Já existe um funcionário cadastrado com a matrícula %s",
                matriculaNova
            );
        }
    }

    /**
     * Busca usuário por ID ou lança exceção
     */
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error(100, "Funcionario", "Usuário com ID %d não encontrado", idUsuario)
            ));
    }

    /**
     * Busca usuário por ID para atualização ou lança exceção
     */
    public Usuario buscarUsuarioPorIdAtualizacao(Integer idUsuario, Integer idFuncionario) {
        return usuarioRepository.findById(idUsuario)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error(idFuncionario, "Funcionario", "Usuário com ID %d não encontrado", idUsuario)
            ));
    }

    /**
     * Busca funcionário por ID ou lança exceção
     */
    public Funcionario buscarFuncionarioPorId(Integer idFuncionario) {
        validarIdFuncionarioNaoNulo(idFuncionario);
        
        return funcionarioRepository.findById(idFuncionario)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error("Funcionário com ID %d não encontrado", idFuncionario)
            ));
    }

    /**
     * Busca funcionário por matrícula ou lança exceção
     */
    public Funcionario buscarFuncionarioPorMatricula(String matricula) {
        validarMatriculaNaoVazia(matricula);
        
        return funcionarioRepository.findByMatricula(matricula)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error("Funcionário com matrícula %s não encontrado", matricula)
            ));
    }

    /**
     * Valida se o ID do usuário é válido para geração de ID do funcionário
     */
    public void validarIdUsuarioParaGeracao(Integer idUsuario) {
        String idUsuarioStr = String.valueOf(idUsuario);
        
        if (idUsuarioStr.length() < 3) {
            systemError.error("ID do usuário inválido para geração de ID do funcionário");
        }
    }

    /**
     * Verifica se o ID do funcionário já existe
     */
    public boolean idFuncionarioExiste(Integer idFuncionario) {
        return funcionarioRepository.existsById(idFuncionario);
    }
}