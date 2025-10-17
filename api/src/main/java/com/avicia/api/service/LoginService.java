package com.avicia.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.request.login.LoginAdminRequest;
import com.avicia.api.data.dto.request.login.LoginRequest;
import com.avicia.api.data.model.Usuario;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public boolean loginCorreto(LoginRequest loginRequest, Argon2PasswordEncoder passwordEncoder) {
        
        Usuario usuario = usuarioRepository.findByCpf(loginRequest.cpf())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        return passwordEncoder.matches(loginRequest.senha(), usuario.getSenhaHash());
    }

    public boolean loginAdminCorreto(LoginAdminRequest loginAdminRequest, Argon2PasswordEncoder passwordEncoder) {
        
        Usuario usuario = usuarioRepository.findByNome(loginAdminRequest.nome())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        return  passwordEncoder.matches(loginAdminRequest.senha(), usuario.getSenhaHash());
    }

}
