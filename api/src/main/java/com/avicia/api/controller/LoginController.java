package com.avicia.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private final JwtEncoder jwtEncoder;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private final LoginService loginService;

    @Autowired
    private final TokenService tokenService;

    @PostMapping("/login") // localhost:9081/api/login
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        var usuario = usuarioRepository.findByCpf(loginRequest.cpf());

        var roleEntity = usuario.get().getIdRole();

        var roleDTO = new RoleDTO();
        roleDTO.setIdRole(roleEntity.getIdRole());
        roleDTO.setNome(roleEntity.getNome());
        roleDTO.setDescricao(roleEntity.getDescricao());
        roleDTO.setPermissoes(roleEntity.getPermissoes());

        if (usuario.isEmpty() || !loginService.loginCorreto(loginRequest, bCryptPasswordEncoder)) {
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

        if (usuario.isEmpty() || !loginService.loginAdminCorreto(loginAdminRequest, bCryptPasswordEncoder)) {
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


