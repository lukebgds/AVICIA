package com.avicia.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.request.AlterarSenhaRequest;
import com.avicia.api.data.dto.request.UsuarioRequest;
import com.avicia.api.data.dto.response.UsuarioResponse;
import com.avicia.api.service.UsuarioService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping // localhost:9081/api/usuarios
    public ResponseEntity<UsuarioResponse> criar(@RequestBody UsuarioRequest dto) {
        return ResponseEntity.ok(usuarioService.criar(dto));
    }

    @GetMapping // localhost:9081/api/usuarios
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
    
    @GetMapping("/{cpf}") // localhost:9081/api/usuarios/{cpf}
    public ResponseEntity<UsuarioResponse> buscarPorCpf(@PathVariable String cpf) {
        
        return usuarioService.buscaPorCpf(cpf)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{cpf}") // localhost:9081/api/usuarios/{cpf}
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable String cpf, @RequestBody UsuarioRequest dto) {
        
        return usuarioService.atualizar(cpf, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    // localhost:9081/api/usuarios/{cpf}/senha
    @PutMapping("/{cpf}/senha")
    public ResponseEntity<UsuarioResponse> atualizarSenha(@PathVariable String cpf, @RequestBody AlterarSenhaRequest request) {
        
        return usuarioService.atualizarSenha(cpf, request.senhaAtual(), request.senhaNova())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {

        boolean deletado = usuarioService.deletar(cpf);

        return deletado ? ResponseEntity.noContent().build()
                        : ResponseEntity.notFound().build();
    }

}
