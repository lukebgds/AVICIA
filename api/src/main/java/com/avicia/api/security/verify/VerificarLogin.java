package com.avicia.api.security.verify;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

import com.avicia.api.data.dto.request.login.LoginAdminRequest;
import com.avicia.api.data.dto.request.login.LoginRequest;
import com.avicia.api.exception.SystemError;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarLogin {

    private final UsuarioRepository usuarioRepository;
    private final Argon2PasswordEncoder passwordEncoder;
    private final SystemError systemError;

    public void validarLoginRequestNaoNulo(LoginRequest loginRequest) {
        if (loginRequest == null) {
            systemError.error("Request de login não pode ser nulo");
        }
    }

    public void validarLoginAdminRequestNaoNulo(LoginAdminRequest loginAdminRequest) {
        if (loginAdminRequest == null) {
            systemError.error("Request de login não pode ser nulo");
        }
    }

    public void validarCpfNaoVazio(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            systemError.error("CPF não pode ser vazio");
        }
    }

    public void validarNomeNaoVazio(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            systemError.error("Nome não pode ser vazio");
        }
    }

    public void validarSenhaNaoVazia(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            systemError.error("Senha não pode ser vazia");
        }
    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        var usuario = usuarioRepository.findByCpf(cpf).orElse(null);
        if (usuario == null) {
            systemError.error(100, "Login", "CPF ou senha inválidos");
        }
        return usuario;
    }

    public Usuario buscarUsuarioPorNome(String nome) {
        var usuario = usuarioRepository.findByNome(nome).orElse(null);
        if (usuario == null) {
            systemError.error(100, "LoginAdmin", "Nome ou senha inválidos");
        }
        return usuario;
    }

    public void verificarUsuarioAtivo(Usuario usuario, String tipoLogin) {
        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            systemError.error(usuario.getIdUsuario(), tipoLogin,
                "Usuário inativo. Contate o administrador");
        }
    }

    public void verificarSenhaCorreta(String senhaFornecida, String senhaHash, Integer idUsuario, String tipoLogin) {
        if (!passwordEncoder.matches(senhaFornecida, senhaHash)) {
            if ("Login".equals(tipoLogin)) {
                systemError.error(idUsuario, tipoLogin, "CPF ou senha inválidos");
            } else {
                systemError.error(idUsuario, tipoLogin, "Nome ou senha inválidos");
            }
        }
    }

    public void verificarUsuarioTemRole(Usuario usuario) {
        if (usuario.getIdRole() == null) {
            systemError.error(usuario.getIdUsuario(), "Login",
                "Usuário sem permissões configuradas. Contate o administrador");
        }
    }

    public void validarCamposLogin(LoginRequest loginRequest) {
        validarLoginRequestNaoNulo(loginRequest);
        validarCpfNaoVazio(loginRequest.cpf());
        validarSenhaNaoVazia(loginRequest.senha());
    }

    public void validarCamposLoginAdmin(LoginAdminRequest loginAdminRequest) {
        validarLoginAdminRequestNaoNulo(loginAdminRequest);
        validarNomeNaoVazio(loginAdminRequest.nome());
        validarSenhaNaoVazia(loginAdminRequest.senha());
    }
}
