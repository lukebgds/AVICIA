package com.avicia.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.request.role.RoleRequest;
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
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ResponseEntity<CriarRoleResponse> criar(@RequestBody RoleRequest dto) {
        return ResponseEntity.ok(roleService.criar(dto));
    }

    @GetMapping // localhost:9081/api/roles
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<List<RoleResponse>> listarTodos() {
        return ResponseEntity.ok(roleService.listarTodos());
    }

    @GetMapping("/{nome}") // localhost:9081/api/roles/{nome}
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<RoleResponse> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(roleService.buscarPorNome(nome));
    }

    @PutMapping("/{nome}") // localhost:9081/api/roles/{nome}
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResponseEntity<RoleResponse> atualizar(@PathVariable String nome, @RequestBody RoleRequest dto) {
        return ResponseEntity.ok(roleService.atualizar(nome, dto));
    }

    @DeleteMapping("/{nome}") // localhost:9081/api/roles/{nome}
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable String nome) {
        roleService.deletar(nome);
        return ResponseEntity.noContent().build();            
    }

}
