package com.avicia.api.service;

import java.time.Instant;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.dto.request.login.LoginAdminRequest;
import com.avicia.api.data.dto.request.login.LoginRequest;
import com.avicia.api.data.dto.request.role.TokenRoleRequest;
import com.avicia.api.data.dto.response.login.LoginResponse;
import com.avicia.api.exception.BusinessException;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    
    private final UsuarioRepository usuarioRepository;
    private final SystemLogService systemLogService;
    private final TokenService tokenService;
    private final Argon2PasswordEncoder passwordEncoder;
    
    @Transactional
    public LoginResponse realizarLogin(LoginRequest loginRequest) {
        
        // Validações
        if (loginRequest == null) {
            throw new BusinessException("Request de login não pode ser nulo");
        }
        
        if (loginRequest.cpf() == null || loginRequest.cpf().trim().isEmpty()) {
            throw new BusinessException("CPF não pode ser vazio");
        }
        
        if (loginRequest.senha() == null || loginRequest.senha().trim().isEmpty()) {
            throw new BusinessException("Senha não pode ser vazia");
        }
        
        // Busca o usuário
        Usuario usuario = usuarioRepository.findByCpf(loginRequest.cpf())
            .orElseThrow(() -> {
                systemLogService.registrarErro(
                    null,
                    "Login",
                    "Tentativa de login com CPF não cadastrado: " + loginRequest.cpf()
                );
                return new BusinessException("CPF ou senha inválidos");
            });
        
        // Verifica se o usuário está ativo
        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            systemLogService.registrarErro(
                usuario.getIdUsuario(),
                "Login",
                "Tentativa de login de usuário inativo: " + loginRequest.cpf()
            );
            throw new BusinessException("Usuário inativo. Contate o administrador");
        }
        
        // Verifica a senha
        boolean senhaCorreta = passwordEncoder.matches(loginRequest.senha(), usuario.getSenhaHash());
        
        if (!senhaCorreta) {
            systemLogService.registrarErro(
                usuario.getIdUsuario(),
                "Login",
                "Tentativa de login falhou — senha incorreta para CPF: " + loginRequest.cpf()
            );
            throw new BusinessException("CPF ou senha inválidos");
        }
        
        // Prepara os dados da role para o token
        TokenRoleRequest roleRequest = criarTokenRoleRequest(usuario);
        
        // Gera o token
        String jwtValue = tokenService.generate(usuario.getCpf(), usuario.getIdUsuario(), roleRequest);
        Instant expiresIn = tokenService.generateExpirationDate();
        
        // Log de sucesso
        systemLogService.registrarCriacao(
            usuario.getIdUsuario(),
            "Login",
            "Login realizado com sucesso para usuário: " + loginRequest.cpf()
        );
        
        return new LoginResponse(jwtValue, expiresIn);
    }
    
    @Transactional
    public LoginResponse realizarLoginAdmin(LoginAdminRequest loginAdminRequest) {
        
        // Validações
        if (loginAdminRequest == null) {
            throw new BusinessException("Request de login não pode ser nulo");
        }
        
        if (loginAdminRequest.nome() == null || loginAdminRequest.nome().trim().isEmpty()) {
            throw new BusinessException("Nome não pode ser vazio");
        }
        
        if (loginAdminRequest.senha() == null || loginAdminRequest.senha().trim().isEmpty()) {
            throw new BusinessException("Senha não pode ser vazia");
        }
        
        // Busca o usuário
        Usuario usuario = usuarioRepository.findByNome(loginAdminRequest.nome())
            .orElseThrow(() -> {
                systemLogService.registrarErro(
                    null,
                    "LoginAdmin",
                    "Tentativa de login admin com nome não cadastrado: " + loginAdminRequest.nome()
                );
                return new BusinessException("Nome ou senha inválidos");
            });
        
        // Verifica se o usuário está ativo
        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            systemLogService.registrarErro(
                usuario.getIdUsuario(),
                "LoginAdmin",
                "Tentativa de login admin de usuário inativo: " + loginAdminRequest.nome()
            );
            throw new BusinessException("Usuário inativo. Contate o administrador");
        }
        
        // Verifica a senha
        boolean senhaCorreta = passwordEncoder.matches(loginAdminRequest.senha(), usuario.getSenhaHash());
        
        if (!senhaCorreta) {
            systemLogService.registrarErro(
                usuario.getIdUsuario(),
                "LoginAdmin",
                "Tentativa de login admin falhou — senha incorreta para: " + loginAdminRequest.nome()
            );
            throw new BusinessException("Nome ou senha inválidos");
        }
        
        // Prepara os dados da role para o token
        TokenRoleRequest roleRequest = criarTokenRoleRequest(usuario);
        
        // Gera o token
        String jwtValue = tokenService.generate(usuario.getNome(), usuario.getIdUsuario(), roleRequest);
        Instant expiresIn = tokenService.generateExpirationDate();
        
        // Log de sucesso
        systemLogService.registrarCriacao(
            usuario.getIdUsuario(),
            "LoginAdmin",
            "Login admin realizado com sucesso para usuário: " + loginAdminRequest.nome()
        );
        
        return new LoginResponse(jwtValue, expiresIn);
    }
    
    // ================= MÉTODOS AUXILIARES ================= //
    
    private TokenRoleRequest criarTokenRoleRequest(Usuario usuario) {
        if (usuario.getIdRole() == null) {
            systemLogService.registrarErro(
                usuario.getIdUsuario(),
                "Login",
                "Usuário sem role associada: " + usuario.getCpf()
            );
            throw new BusinessException("Usuário sem permissões configuradas. Contate o administrador");
        }
        
        TokenRoleRequest roleRequest = new TokenRoleRequest();
        roleRequest.setIdRole(usuario.getIdRole().getIdRole());
        roleRequest.setNome(usuario.getIdRole().getNome());
        roleRequest.setDescricao(usuario.getIdRole().getDescricao());
        roleRequest.setPermissoes(usuario.getIdRole().getPermissoes());
        
        return roleRequest;
    }
}