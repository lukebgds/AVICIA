package com.avicia.api.security.verify;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.model.ProfissionalSaude;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.ProfissionalSaudeRepository;
import com.avicia.api.repository.UsuarioRepository;
import com.avicia.api.util.Throwing;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarProfissionalSaude {

    private final ProfissionalSaudeRepository profissionalRepository;
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
     * Valida se o registro do conselho não é nulo ou vazio
     */
    public void validarRegistroConselhoNaoVazio(String registroConselho) {
        if (registroConselho == null || registroConselho.trim().isEmpty()) {
            systemError.error("Registro do conselho não pode ser vazio");
        }
    }

    /**
     * Valida se o ID do profissional não é nulo
     */
    public void validarIdProfissionalNaoNulo(Integer idProfissional) {
        if (idProfissional == null) {
            systemError.error("ID do profissional não pode ser nulo");
        }
    }

    /**
     * Verifica se a matrícula já existe
     */
    public void verificarMatriculaDuplicada(String matricula) {
        if (profissionalRepository.findByMatricula(matricula).isPresent()) {
            systemError.error(
                100,
                "ProfissionalSaude",
                "Já existe um profissional cadastrado com a matrícula %s",
                matricula
            );
        }
    }

    /**
     * Verifica se a nova matrícula já existe (exceto para o próprio profissional)
     */
    public void verificarMatriculaDuplicadaAtualizacao(String matriculaAtual, String matriculaNova, Integer idProfissional) {
        if (!matriculaNova.equals(matriculaAtual) && profissionalRepository.findByMatricula(matriculaNova).isPresent()) {
            systemError.error(
                idProfissional,
                "ProfissionalSaude",
                "Já existe um profissional cadastrado com a matrícula %s",
                matriculaNova
            );
        }
    }

    /**
     * Verifica se o registro do conselho já existe
     */
    public void verificarRegistroConselhoDuplicado(String registroConselho) {
        if (profissionalRepository.findByRegistroConselho(registroConselho).isPresent()) {
            systemError.error(
                100,
                "ProfissionalSaude",
                "Já existe um profissional cadastrado com o registro de conselho %s",
                registroConselho
            );
        }
    }

    /**
     * Verifica se o novo registro do conselho já existe (exceto para o próprio profissional)
     */
    public void verificarRegistroConselhoDuplicadoAtualizacao(String registroAtual, String registroNovo, Integer idProfissional) {
        if (!registroNovo.equals(registroAtual) && profissionalRepository.findByRegistroConselho(registroNovo).isPresent()) {
            systemError.error(
                idProfissional,
                "ProfissionalSaude",
                "Já existe um profissional cadastrado com o registro de conselho %s",
                registroNovo
            );
        }
    }

    /**
     * Busca usuário por ID ou lança exceção
     */
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error(idUsuario, "ProfissionalSaude", "Usuário com ID %d não encontrado", idUsuario)
            ));
    }

    /**
     * Busca usuário por ID para atualização ou lança exceção
     */
    public Usuario buscarUsuarioPorIdAtualizacao(Integer idUsuario, Integer idProfissional) {
        return usuarioRepository.findById(idUsuario)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error(idProfissional, "ProfissionalSaude", "Usuário com ID %d não encontrado", idUsuario)
            ));
    }

    /**
     * Busca profissional por ID ou lança exceção
     */
    public ProfissionalSaude buscarProfissionalPorId(Integer idProfissional) {
        validarIdProfissionalNaoNulo(idProfissional);
        
        return profissionalRepository.findByIdProfissional(idProfissional)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error("Profissional de saúde com ID %d não encontrado", idProfissional)
            ));
    }

    /**
     * Busca profissional por matrícula ou lança exceção
     */
    public ProfissionalSaude buscarProfissionalPorMatricula(String matricula) {
        validarMatriculaNaoVazia(matricula);
        
        return profissionalRepository.findByMatricula(matricula)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error("Profissional de saúde com matrícula %s não encontrado", matricula)
            ));
    }

    /**
     * Busca profissional por registro do conselho ou lança exceção
     */
    public ProfissionalSaude buscarProfissionalPorRegistroConselho(String registroConselho) {
        validarRegistroConselhoNaoVazio(registroConselho);
        
        return profissionalRepository.findByRegistroConselho(registroConselho)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error("Profissional de saúde com registro de conselho %s não encontrado", registroConselho)
            ));
    }

    /**
     * Valida se o ID do usuário é válido para geração de ID do profissional
     */
    public void validarIdUsuarioParaGeracao(Integer idUsuario) {
        String idUsuarioStr = String.valueOf(idUsuario);
        
        if (idUsuarioStr.length() < 3) {
            systemError.error("ID do usuário inválido para geração de ID do profissional");
        }
    }

    /**
     * Verifica se o ID do profissional já existe
     */
    public boolean idProfissionalExiste(Integer idProfissional) {
        return profissionalRepository.existsById(idProfissional);
    }
}