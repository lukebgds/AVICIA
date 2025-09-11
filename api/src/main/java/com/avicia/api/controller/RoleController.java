package com.avicia.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.RoleDTO;
import com.avicia.api.data.mapper.RoleMapper;
import com.avicia.api.model.Role;
import com.avicia.api.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @PostMapping // localhost:9081/api/roles
    public ResponseEntity<RoleDTO> criar(@RequestBody RoleDTO dto) {
        
        Role role = RoleMapper.toEntity(dto);
        Role salvo = roleRepository.save(role);

        return ResponseEntity.ok(RoleMapper.toDTO(salvo));
    }

    @GetMapping // localhost:9081/api/roles
    public ResponseEntity<List<RoleDTO>> listarTodos() {
        
        List<RoleDTO> lista = roleRepository.findAll()
                .stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{nome}") // localhost:9081/api/roles/{nome}
    public ResponseEntity<RoleDTO> buscarPorNome(@PathVariable String nome) {
        
        Optional<Role> role = roleRepository.findByNome(nome);

        return role.map(r -> ResponseEntity.ok(RoleMapper.toDTO(r)))
                   .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{nome}") // localhost:9081/api/roles/{nome}
    public ResponseEntity<RoleDTO> atualizar(@PathVariable String nome, @RequestBody RoleDTO dto) {
        
        return roleRepository.findByNome(nome).map(role -> {
            role.setNome(dto.getNome());
            role.setDescricao(dto.getDescricao());
            role.setPermissoes(dto.getPermissoes());

            Role atualizado = roleRepository.save(role);
            return ResponseEntity.ok(RoleMapper.toDTO(atualizado));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{nome}") // localhost:9081/api/roles/{nome}
    public ResponseEntity<Void> deletar(@PathVariable String name) {
        
        return roleRepository.findByNome(name).map(role -> {
            roleRepository.delete(role);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
