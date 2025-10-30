package com.avicia.api.features.profissional;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.features.usuario.Usuario;
import com.avicia.api.features.usuario.UsuarioRepository;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarProfissionalSaude {

    private final ProfissionalSaudeRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemError systemError;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    /**
     * Valida se o ID do usuário não é nulo
     */
    public void validarIdUsuarioNaoNulo(Integer idUsuario) {
        Integer id = getIdUsuarioToken();
        if (idUsuario == null) {
            systemError.error(id, "Profissional de Saúde","ID do usuário não pode ser nulo");
        }
    }

    /**
     * Valida se a matrícula não é nula ou vazia
     */
    public void validarMatriculaNaoVazia(String matricula) {
        Integer id = getIdUsuarioToken();
        if (matricula == null || matricula.trim().isEmpty()) {
            systemError.error(id, "Profissional de Saúde","Matrícula não pode ser vazia");
        }
    }

    /**
     * Valida se o registro do conselho não é nulo ou vazio
     */
    public void validarRegistroConselhoNaoVazio(String registroConselho) {
        Integer id = getIdUsuarioToken();
        if (registroConselho == null || registroConselho.trim().isEmpty()) {
            systemError.error(id, "Profissional de Saúde","Registro do conselho não pode ser vazio");
        }
    }

    /**
     * Valida se o ID do profissional não é nulo
     */
    public void validarIdProfissionalNaoNulo(Integer idProfissional) {
        Integer id = getIdUsuarioToken();
        if (idProfissional == null) {
            systemError.error(id, "Profissional de Saúde","ID do profissional não pode ser nulo");
        }
    }

    /**
     * Verifica se a matrícula já existe
     */
    public void verificarMatriculaDuplicada(String matricula) {
        Integer id = getIdUsuarioToken();
        if (profissionalRepository.findByMatricula(matricula).isPresent()) {
            systemError.error(
                id,
                "Profissional de Saúde",
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
                "Profissional de Saúde",
                "Já existe um profissional cadastrado com a matrícula %s",
                matriculaNova
            );
        }
    }

    /**
     * Verifica se o registro do conselho já existe
     */
    public void verificarRegistroConselhoDuplicado(String registroConselho) {
        Integer id = getIdUsuarioToken();
        if (profissionalRepository.findByRegistroConselho(registroConselho).isPresent()) {
            systemError.error(
                id,
                "Profissional de Saúde",
                "Já existe um profissional cadastrado com o registro de conselho %s",
                registroConselho
            );
        }
    }

    /**
     * Verifica se o novo registro do conselho já existe (exceto para o próprio profissional)
     */
    public void verificarRegistroConselhoDuplicadoAtualizacao(String registroAtual, String registroNovo, Integer idProfissional) {
        Integer id = getIdUsuarioToken();
        if (!registroNovo.equals(registroAtual) && profissionalRepository.findByRegistroConselho(registroNovo).isPresent()) {
            systemError.error(
                id,
                "Profissional de Saúde",
                "Já existe um profissional cadastrado com o registro de conselho %s",
                registroNovo
            );
        }
    }

    /**
     * Busca usuário por ID ou lança exceção
     */
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(idUsuario, "Profissional de Saúde", "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    /**
     * Busca usuário por ID para atualização ou lança exceção
     */
    public Usuario buscarUsuarioPorIdAtualizacao(Integer idUsuario, Integer idProfissional) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(idUsuario, "Profissional de Saúde", "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    /**
     * Busca profissional por ID ou lança exceção
     */
    public ProfissionalSaude buscarProfissionalPorId(Integer idProfissional) {
        validarIdProfissionalNaoNulo(idProfissional);
        Integer id = getIdUsuarioToken();
        ProfissionalSaude profissionalSaude = profissionalRepository.findById(id).orElse(null);
        if (profissionalSaude == null) {
            systemError.error(id, "Profissional de Saúde", "Profissional de saúde com ID %d não encontrado", idProfissional);
        }
        return profissionalSaude;
    }

    /**
     * Busca profissional por matrícula ou lança exceção
     */
    public ProfissionalSaude buscarProfissionalPorMatricula(String matricula) {
        validarMatriculaNaoVazia(matricula);
        Integer id = getIdUsuarioToken();
        ProfissionalSaude profissionalSaude = profissionalRepository.findByMatricula(matricula).orElse(null);
        if (profissionalSaude == null) {
            systemError.error(id, "Profissional de Saúde","Profissional de saúde com matrícula %s não encontrado", matricula);
        }
        return profissionalSaude;
    }

    /**
     * Busca profissional por registro do conselho ou lança exceção
     */
    public ProfissionalSaude buscarProfissionalPorRegistroConselho(String registroConselho) {
        validarRegistroConselhoNaoVazio(registroConselho);
        Integer id = getIdUsuarioToken();
        ProfissionalSaude profissionalSaude = profissionalRepository.findByRegistroConselho(registroConselho).orElse(null);
        if (profissionalSaude == null) {
            systemError.error(id, "Profissional de Saúde","Profissional de saúde com registro de conselho %s não encontrado", registroConselho);
        }
        return profissionalSaude;
    }

    /**
     * Valida se o ID do usuário é válido para geração de ID do profissional
     */
    public void validarIdUsuarioParaGeracao(Integer idUsuario) {
        String idUsuarioStr = String.valueOf(idUsuario);
        Integer id = getIdUsuarioToken();
        if (idUsuarioStr.length() < 3) {
            systemError.error(id, "Profissional de Saúde","ID do usuário inválido para geração de ID do profissional");
        }
    }

    /**
     * Verifica se o ID do profissional já existe
     */
    public boolean idProfissionalExiste(Integer idProfissional) {
        return profissionalRepository.existsById(idProfissional);
    }

    /**
     * Resgatar o ID de Usuário pelo token de sessão
     */
    protected Integer getIdUsuarioToken () {
        return usuarioAutenticadoUtil.getIdUsuario();
    }
}