package com.avicia.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.object.RoleDTO;
import com.avicia.api.data.dto.request.RoleResquest;
import com.avicia.api.data.dto.response.role.CriarRoleResponse;
import com.avicia.api.data.dto.response.role.RoleResponse;
import com.avicia.api.service.RoleService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping // localhost:9081/api/roles
    public ResponseEntity<CriarRoleResponse> criar(@RequestBody RoleResquest dto) {
        return ResponseEntity.ok(roleService.criar(dto));
    }

    @GetMapping // localhost:9081/api/roles
    public ResponseEntity<List<RoleResponse>> listarTodos() {
        return ResponseEntity.ok(roleService.listarTodos());
    }

    @GetMapping("/{nome}") // localhost:9081/api/roles/{nome}
    public ResponseEntity<RoleResponse> buscarPorNome(@PathVariable String nome) {
        
        return roleService.buscarPorNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{nome}") // localhost:9081/api/roles/{nome}
    public ResponseEntity<RoleResponse> atualizar(@PathVariable String nome, @RequestBody RoleDTO dto) {
        
        return roleService.atualizar(nome, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{nome}") // localhost:9081/api/roles/{nome}
    public ResponseEntity<Void> deletar(@PathVariable String nome) {
        
        boolean deletado = roleService.deletar(nome);

        return deletado ? ResponseEntity.noContent().build()
                        : ResponseEntity.notFound().build();
    }

}
