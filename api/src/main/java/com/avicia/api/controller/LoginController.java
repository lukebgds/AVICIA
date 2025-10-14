package com.avicia.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.object.RoleDTO;
import com.avicia.api.data.dto.request.LoginAdminRequest;
import com.avicia.api.data.dto.request.LoginRequest;
import com.avicia.api.data.dto.response.LoginResponse;
import com.avicia.api.repository.UsuarioRepository;
import com.avicia.api.service.LoginService;
import com.avicia.api.service.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final LoginService loginService;
    private final TokenService tokenService;
    private final Argon2PasswordEncoder passwordEncoder;

    @PostMapping("/login") // localhost:9081/api/login
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        var usuario = usuarioRepository.findByCpf(loginRequest.cpf());

        var roleEntity = usuario.get().getIdRole();

        var roleDTO = new RoleDTO();
        roleDTO.setIdRole(roleEntity.getIdRole());
        roleDTO.setNome(roleEntity.getNome());
        roleDTO.setDescricao(roleEntity.getDescricao());
        roleDTO.setPermissoes(roleEntity.getPermissoes());

        if (usuario.isEmpty() || !loginService.loginCorreto(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var jwtValue = tokenService.generate(usuario.get().getCpf(), roleDTO);
        var expiresIn = tokenService.generateExpirationDate();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    @PostMapping("/admin/login") // localhost:9081/api/admin/login
    public ResponseEntity<LoginResponse> loginAdmin(@RequestBody LoginAdminRequest loginAdminRequest) {
        
        var usuario = usuarioRepository.findByNome(loginAdminRequest.nome());

        var roleEntity = usuario.get().getIdRole();

        var roleDTO = new RoleDTO();
        roleDTO.setIdRole(roleEntity.getIdRole());
        roleDTO.setNome(roleEntity.getNome());
        roleDTO.setDescricao(roleEntity.getDescricao());
        roleDTO.setPermissoes(roleEntity.getPermissoes());

        if (usuario.isEmpty() || !loginService.loginAdminCorreto(loginAdminRequest, passwordEncoder)) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var jwtValue = tokenService.generate(usuario.get().getNome(), roleDTO);
        var expiresIn = tokenService.generateExpirationDate();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    /* 
        @GetMapping("/teste-jwt")
        public String testeJwt() {
            return tokenService.generate("teste");
        }
    */


}


