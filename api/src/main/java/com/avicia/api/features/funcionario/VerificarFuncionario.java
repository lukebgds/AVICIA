package com.avicia.api.features.funcionario;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.usuario.Usuario;
import com.avicia.api.features.usuario.UsuarioRepository;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarFuncionario {

    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemError systemError;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    /**
     * Valida se o ID do usuário não é nulo
     */
    public void validarIdUsuarioNaoNulo(Integer idUsuario) {
        Integer id = getIdUsusarioToken();
        if (idUsuario == null) {
            systemError.error(id, "Funcionário","ID do usuário não pode ser nulo");
        }
    }

    /**
     * Valida se a matrícula não é nula ou vazia
     */
    public void validarMatriculaNaoVazia(String matricula) {
        Integer id = getIdUsusarioToken();
        if (matricula == null || matricula.trim().isEmpty()) {
            systemError.error(id, "Funcionário","Matrícula não pode ser vazia");
        }
    }

    /**
     * Valida se o ID do funcionário não é nulo
     */
    public void validarIdFuncionarioNaoNulo(Integer idFuncionario) {
        Integer id = getIdUsusarioToken();
        if (idFuncionario == null) {
            systemError.error(id, "Funcionário","ID do funcionário não pode ser nulo");
        }
    }

    /**
     * Verifica se a matrícula já existe
     */
    public void verificarMatriculaDuplicada(String matricula) {
        Integer id = getIdUsusarioToken();
        if (funcionarioRepository.findByMatricula(matricula).isPresent()) {
            systemError.error(
                id,
                "Funcionário",
                "Já existe um funcionário cadastrado com a matrícula %s",
                matricula
            );
        }
    }

    /**
     * Verifica se a nova matrícula já existe (exceto para o próprio funcionário)
     */
    public void verificarMatriculaDuplicadaAtualizacao(String matriculaAtual, String matriculaNova, Integer idFuncionario) {
        Integer id = getIdUsusarioToken();
        if (!matriculaNova.equals(matriculaAtual) && funcionarioRepository.findByMatricula(matriculaNova).isPresent()) {
            systemError.error(
                id,
                "Funcionário",
                "Já existe um funcionário cadastrado com a matrícula %s",
                matriculaNova
            );
        }
    }

    /**
     * Busca usuário por ID ou lança exceção
     */
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        Integer id = getIdUsusarioToken();
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(id, "Funcionário", "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    /**
     * Busca usuário por ID para atualização ou lança exceção
     */
    public Usuario buscarUsuarioPorIdAtualizacao(Integer idUsuario, Integer idFuncionario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(idUsuario, "Funcionário", "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    /**
     * Busca funcionário por ID ou lança exceção
     */
    public Funcionario buscarFuncionarioPorId(Integer idFuncionario) {
        validarIdFuncionarioNaoNulo(idFuncionario);
        Integer id = getIdUsusarioToken();
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario).orElse(null);
        if (funcionario == null) {
            systemError.error(id, "Funcionário","Funcionário com ID %d não encontrado", idFuncionario);
        }
        return funcionario;
    }

    /**
     * Busca funcionário por matrícula ou lança exceção
     */
    public Funcionario buscarFuncionarioPorMatricula(String matricula) {
        validarMatriculaNaoVazia(matricula);
        Integer id = getIdUsusarioToken();
        Funcionario funcionario = funcionarioRepository.findByMatricula(matricula).orElse(null);
        if (funcionario == null) {
            systemError.error(id, "Funcionário", "Funcionário com matrícula %s não encontrado", matricula);
        }
        return funcionario;
    }

    /**
     * Valida se o ID do usuário é válido para geração de ID do funcionário
     */
    public void validarIdUsuarioParaGeracao(Integer idUsuario) {
        String idUsuarioStr = String.valueOf(idUsuario);
        Integer id = getIdUsusarioToken();
        if (idUsuarioStr.length() < 3) {
            systemError.error(id, "Funcionário","ID do usuário inválido para geração de ID do funcionário");
        }
    }

    /**
     * Verifica se o ID do funcionário já existe
     */
    public boolean idFuncionarioExiste(Integer idFuncionario) {
        return funcionarioRepository.existsById(idFuncionario);
    }

    /**
     * Resgatar o ID de Usuário pelo token de sessão 
     */
    protected Integer getIdUsusarioToken () {
        return usuarioAutenticadoUtil.getIdUsuario();
    }
}