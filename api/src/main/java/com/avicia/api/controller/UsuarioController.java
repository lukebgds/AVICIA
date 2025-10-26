package com.avicia.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.request.senha.AlterarSenhaRequest;
import com.avicia.api.data.dto.request.usuario.UsuarioRequest;
import com.avicia.api.data.dto.response.usuario.CriarUsuarioResponse;
import com.avicia.api.data.dto.response.usuario.UsuarioResponse;
import com.avicia.api.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/cadastro")// localhost:9081/api/usuarios
    public ResponseEntity<CriarUsuarioResponse> criar(@RequestBody UsuarioRequest dto) {
        return ResponseEntity.ok(usuarioService.criar(dto));
    }

    @GetMapping // localhost:9081/api/usuarios
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
    
    @GetMapping("/{cpf}")
    public ResponseEntity<UsuarioResponse> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(usuarioService.buscaPorCpf(cpf));
    }

    @PutMapping("/{cpf}") // localhost:9081/api/usuarios/{cpf}
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable String cpf, @RequestBody UsuarioRequest dto) {
        return ResponseEntity.ok(usuarioService.atualizar(cpf, dto));
    }

    // localhost:9081/api/usuarios/{cpf}/senha
    @PutMapping("/{cpf}/senha")
    public ResponseEntity<UsuarioResponse> atualizarSenha(@PathVariable String cpf, @RequestBody AlterarSenhaRequest request) {
        UsuarioResponse usuario = usuarioService.atualizarSenha(cpf, request.senhaAtual(), request.senhaNova());
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{idUsuario}") // localhost:9081/api/usuarios/{cpf}
    public ResponseEntity<Void> deletar(@PathVariable Integer idUsuario) {
        usuarioService.deletar(idUsuario);
        return ResponseEntity.noContent().build();
    }

}
