package com.avicia.api.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.dto.request.login.LoginAdminRequest;
import com.avicia.api.data.dto.request.login.LoginRequest;
import com.avicia.api.data.dto.request.role.TokenRoleRequest;
import com.avicia.api.data.dto.response.login.LoginResponse;
import com.avicia.api.model.Usuario;
import com.avicia.api.security.verify.VerificarLogin;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    
    private final SystemLogService systemLogService;
    private final TokenService tokenService;
    private final VerificarLogin verificarLogin;
    
    @Transactional
    public LoginResponse realizarLogin(LoginRequest loginRequest) {
        
        // Validações
        verificarLogin.validarCamposLogin(loginRequest);
        
        // Busca o usuário
        Usuario usuario = verificarLogin.buscarUsuarioPorCpf(loginRequest.cpf());
        
        // Verifica se o usuário está ativo
        verificarLogin.verificarUsuarioAtivo(usuario, "Login");
        
        // Verifica a senha
        verificarLogin.verificarSenhaCorreta(
            loginRequest.senha(),
            usuario.getSenhaHash(),
            usuario.getIdUsuario(),
            "Login"
        );
        
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
        verificarLogin.validarCamposLoginAdmin(loginAdminRequest);
        
        // Busca o usuário
        Usuario usuario = verificarLogin.buscarUsuarioPorNome(loginAdminRequest.nome());
        
        // Verifica se o usuário está ativo
        verificarLogin.verificarUsuarioAtivo(usuario, "LoginAdmin");
        
        // Verifica a senha
        verificarLogin.verificarSenhaCorreta(
            loginAdminRequest.senha(),
            usuario.getSenhaHash(),
            usuario.getIdUsuario(),
            "LoginAdmin"
        );
        
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
        verificarLogin.verificarUsuarioTemRole(usuario);
        
        TokenRoleRequest roleRequest = new TokenRoleRequest();
        roleRequest.setIdRole(usuario.getIdRole().getIdRole());
        roleRequest.setNome(usuario.getIdRole().getNome());
        roleRequest.setDescricao(usuario.getIdRole().getDescricao());
        roleRequest.setPermissoes(usuario.getIdRole().getPermissoes());
        
        return roleRequest;
    }
}