package com.avicia.api.security.verify;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

import com.avicia.api.data.dto.request.login.LoginAdminRequest;
import com.avicia.api.data.dto.request.login.LoginRequest;
import com.avicia.api.exception.SystemError;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.UsuarioRepository;
import com.avicia.api.util.Throwing;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarLogin {

    private final UsuarioRepository usuarioRepository;
    private final Argon2PasswordEncoder passwordEncoder;
    private final SystemError systemError;

    /**
     * Valida se o request de login não é nulo
     */
    public void validarLoginRequestNaoNulo(LoginRequest loginRequest) {
        if (loginRequest == null) {
            systemError.error("Request de login não pode ser nulo");
        }
    }

    /**
     * Valida se o request de login admin não é nulo
     */
    public void validarLoginAdminRequestNaoNulo(LoginAdminRequest loginAdminRequest) {
        if (loginAdminRequest == null) {
            systemError.error("Request de login não pode ser nulo");
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
     * Valida se o nome não é nulo ou vazio
     */
    public void validarNomeNaoVazio(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            systemError.error("Nome não pode ser vazio");
        }
    }

    /**
     * Valida se a senha não é nula ou vazia
     */
    public void validarSenhaNaoVazia(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            systemError.error("Senha não pode ser vazia");
        }
    }

    /**
     * Busca usuário por CPF ou lança exceção com mensagem genérica
     */
    public Usuario buscarUsuarioPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error(100, "Login", "CPF ou senha inválidos")
            ));
    }

    /**
     * Busca usuário por nome ou lança exceção com mensagem genérica
     */
    public Usuario buscarUsuarioPorNome(String nome) {
        return usuarioRepository.findByNome(nome)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error(100, "LoginAdmin", "Nome ou senha inválidos")
            ));
    }

    /**
     * Verifica se o usuário está ativo
     */
    public void verificarUsuarioAtivo(Usuario usuario, String tipoLogin) {
        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            systemError.error(
                usuario.getIdUsuario(),
                tipoLogin,
                "Usuário inativo. Contate o administrador"
            );
        }
    }

    /**
     * Verifica se a senha está correta
     */
    public void verificarSenhaCorreta(String senhaFornecida, String senhaHash, Integer idUsuario, String tipoLogin) {
        boolean senhaCorreta = passwordEncoder.matches(senhaFornecida, senhaHash);
        
        if (!senhaCorreta) {
            // Mensagem genérica para não dar dica de qual campo está errado
            if ("Login".equals(tipoLogin)) {
                systemError.error(idUsuario, tipoLogin, "CPF ou senha inválidos");
            } else {
                systemError.error(idUsuario, tipoLogin, "Nome ou senha inválidos");
            }
        }
    }

    /**
     * Verifica se o usuário tem role associada
     */
    public void verificarUsuarioTemRole(Usuario usuario) {
        if (usuario.getIdRole() == null) {
            systemError.error(
                usuario.getIdUsuario(),
                "Login",
                "Usuário sem permissões configuradas. Contate o administrador"
            );
        }
    }

    /**
     * Valida todos os campos do LoginRequest
     */
    public void validarCamposLogin(LoginRequest loginRequest) {
        validarLoginRequestNaoNulo(loginRequest);
        validarCpfNaoVazio(loginRequest.cpf());
        validarSenhaNaoVazia(loginRequest.senha());
    }

    /**
     * Valida todos os campos do LoginAdminRequest
     */
    public void validarCamposLoginAdmin(LoginAdminRequest loginAdminRequest) {
        validarLoginAdminRequestNaoNulo(loginAdminRequest);
        validarNomeNaoVazio(loginAdminRequest.nome());
        validarSenhaNaoVazia(loginAdminRequest.senha());
    }
}