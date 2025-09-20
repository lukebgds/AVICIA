package com.avicia.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.request.LoginAdminRequest;
import com.avicia.api.data.dto.request.LoginRequest;
import com.avicia.api.data.dto.response.LoginResponse;
import com.avicia.api.repository.UsuarioRepository;
import com.avicia.api.service.LoginService;
import com.avicia.api.service.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class LoginController {

    private final JwtEncoder jwtEncoder;

    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final LoginService loginService;

    private final TokenService tokenService;

    @PostMapping("/login") // localhost:9081/api/login
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        var usuraio = usuarioRepository.findByCpf(loginRequest.cpf());

        if (usuraio.isEmpty() || !loginService.loginCorreto(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var jwtValue = tokenService.generate(usuraio.get().getCpf());
        var expiresIn = tokenService.generateExpirationDate();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    @PostMapping("/admin/login") // localhost:9081/api/admin/login
    public ResponseEntity<LoginResponse> loginAdmin(@RequestBody LoginAdminRequest loginAdminRequest) {
        
        var usuraio = usuarioRepository.findByNome(loginAdminRequest.nome());

        if (usuraio.isEmpty() || !loginService.loginAdminCorreto(loginAdminRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var jwtValue = tokenService.generate(usuraio.get().getNome());
        var expiresIn = tokenService.generateExpirationDate();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    @GetMapping("/teste-jwt")
    public String testeJwt() {
        return tokenService.generate("teste");
    }


}


