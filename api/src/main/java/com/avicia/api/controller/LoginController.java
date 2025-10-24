package com.avicia.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.request.login.LoginAdminRequest;
import com.avicia.api.data.dto.request.login.LoginRequest;
import com.avicia.api.data.dto.response.login.LoginResponse;
import com.avicia.api.service.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    
    private final LoginService loginService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.realizarLogin(loginRequest));
    }
    
    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> loginAdmin(@RequestBody LoginAdminRequest loginAdminRequest) {
        return ResponseEntity.ok(loginService.realizarLoginAdmin(loginAdminRequest));
    }
}