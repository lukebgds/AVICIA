package com.avicia.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.request.LoginAdminRequest;
import com.avicia.api.data.dto.request.LoginRequest;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public boolean loginCorreto(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        
        Usuario usuario = usuarioRepository.findByCpf(loginRequest.cpf())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        return passwordEncoder.matches(loginRequest.senha(), usuario.getSenhaHash());
    }

    public boolean loginAdminCorreto(LoginAdminRequest loginAdminRequest, PasswordEncoder passwordEncoder) {
        
        Usuario usuario = usuarioRepository.findByNome(loginAdminRequest.nome())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        return  loginAdminRequest.senha().equals(usuario.getSenhaHash());
    }

}
