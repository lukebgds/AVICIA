package com.avicia.api.security.verify;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.model.ProfissionalSaude;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.ProfissionalSaudeRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarProfissionalSaude {

    private final ProfissionalSaudeRepository profissionalRepository;
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

    public void validarRegistroConselhoNaoVazio(String registroConselho) {
        if (registroConselho == null || registroConselho.trim().isEmpty()) {
            systemError.error("Registro do conselho não pode ser vazio");
        }
    }

    public void validarIdProfissionalNaoNulo(Integer idProfissional) {
        if (idProfissional == null) {
            systemError.error("ID do profissional não pode ser nulo");
        }
    }

    public void verificarMatriculaDuplicada(String matricula) {
        if (profissionalRepository.findByMatricula(matricula).isPresent()) {
            systemError.error(100, "ProfissionalSaude",
                "Já existe um profissional cadastrado com a matrícula %s", matricula);
        }
    }

    public void verificarMatriculaDuplicadaAtualizacao(String matriculaAtual, String matriculaNova, Integer idProfissional) {
        if (!matriculaNova.equals(matriculaAtual) &&
            profissionalRepository.findByMatricula(matriculaNova).isPresent()) {
            systemError.error(idProfissional, "ProfissionalSaude",
                "Já existe um profissional cadastrado com a matrícula %s", matriculaNova);
        }
    }

    public void verificarRegistroConselhoDuplicado(String registroConselho) {
        if (profissionalRepository.findByRegistroConselho(registroConselho).isPresent()) {
            systemError.error(100, "ProfissionalSaude",
                "Já existe um profissional cadastrado com o registro de conselho %s", registroConselho);
        }
    }

    public void verificarRegistroConselhoDuplicadoAtualizacao(String registroAtual, String registroNovo, Integer idProfissional) {
        if (!registroNovo.equals(registroAtual) &&
            profissionalRepository.findByRegistroConselho(registroNovo).isPresent()) {
            systemError.error(idProfissional, "ProfissionalSaude",
                "Já existe um profissional cadastrado com o registro de conselho %s", registroNovo);
        }
    }

    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        var usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(idUsuario, "ProfissionalSaude",
                "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    public Usuario buscarUsuarioPorIdAtualizacao(Integer idUsuario, Integer idProfissional) {
        var usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            systemError.error(idProfissional, "ProfissionalSaude",
                "Usuário com ID %d não encontrado", idUsuario);
        }
        return usuario;
    }

    public ProfissionalSaude buscarProfissionalPorId(Integer idProfissional) {
        validarIdProfissionalNaoNulo(idProfissional);
        var profissional = profissionalRepository.findByIdProfissional(idProfissional).orElse(null);
        if (profissional == null) {
            systemError.error("Profissional de saúde com ID %d não encontrado", idProfissional);
        }
        return profissional;
    }

    public ProfissionalSaude buscarProfissionalPorMatricula(String matricula) {
        validarMatriculaNaoVazia(matricula);
        var profissional = profissionalRepository.findByMatricula(matricula).orElse(null);
        if (profissional == null) {
            systemError.error("Profissional de saúde com matrícula %s não encontrado", matricula);
        }
        return profissional;
    }

    public ProfissionalSaude buscarProfissionalPorRegistroConselho(String registroConselho) {
        validarRegistroConselhoNaoVazio(registroConselho);
        var profissional = profissionalRepository.findByRegistroConselho(registroConselho).orElse(null);
        if (profissional == null) {
            systemError.error("Profissional de saúde com registro de conselho %s não encontrado", registroConselho);
        }
        return profissional;
    }

    public void validarIdUsuarioParaGeracao(Integer idUsuario) {
        String idUsuarioStr = String.valueOf(idUsuario);
        if (idUsuarioStr.length() < 3) {
            systemError.error("ID do usuário inválido para geração de ID do profissional");
        }
    }

    public boolean idProfissionalExiste(Integer idProfissional) {
        return profissionalRepository.existsById(idProfissional);
    }
}
