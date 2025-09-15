package com.avicia.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.UsuarioDTO;
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

    private UsuarioService usuarioService;

    
    @PostMapping // localhost:9081/api/usuarios
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.criar(dto));
    }

    @GetMapping // localhost:9081/api/usuarios
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
    
    @GetMapping("/{cpf}") // localhost:9081/api/usuarios/{cpf}
    public ResponseEntity<UsuarioDTO> buscarPorCpf(@PathVariable String cpf) {
        
        return usuarioService.buscaPorCpf(cpf)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{cpf}") // localhost:9081/api/usuarios/{cpf}
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable String cpf, @RequestBody UsuarioDTO dto) {
        
        return usuarioService.atualizar(cpf, dto)
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
