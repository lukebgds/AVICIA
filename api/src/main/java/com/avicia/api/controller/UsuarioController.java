package com.avicia.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.UsuarioDTO;
import com.avicia.api.data.mapper.UsuarioMapper;
import com.avicia.api.model.Role;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.RoleRepository;
import com.avicia.api.repository.UsuarioRepository;

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
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final RoleRepository roleRepository;

    
    @PostMapping // localhost:9081/api/usuarios
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO dto) {
        
        Usuario usuario = UsuarioMapper.toEntity(dto);
        Role role = roleRepository.findByIdRole(dto.getIdRole())
            .orElseThrow(() -> new RuntimeException("Role não Encontrada"));
        usuario.setIdRole(role);    

        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(UsuarioMapper.toDTO(salvo));
    }

    @GetMapping // localhost:9081/api/usuarios
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        
        List<UsuarioDTO> lista = usuarioRepository.findAll()
            .stream()
            .map(UsuarioMapper :: toDTO)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/{cpf}") // localhost:9081/api/usuarios/{cpf}
    public ResponseEntity<UsuarioDTO> buscarPorCpf(@PathVariable String cpf) {

        Optional<Usuario> usuario = usuarioRepository.findByCpf(cpf);

        return usuario.map(u -> ResponseEntity.ok(UsuarioMapper.toDTO(u)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{cpf}") // localhost:9081/api/usuarios/{cpf}
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable String cpf, @RequestBody UsuarioDTO dto) {
        
        return usuarioRepository.findByCpf(cpf).map(usuario -> {
            usuario.setNome(dto.getNome());
            usuario.setSobrenome(dto.getSobrenome());
            usuario.setCpf(dto.getCpf());
            usuario.setEmail(dto.getEmail());
            usuario.setTelefone(dto.getTelefone());
            usuario.setAtivo(dto.getAtivo());
            usuario.setMfaHabilitado(dto.getMfaHabilitado());
            usuario.setDataCriacao(dto.getDataCriacao());

            Role role = roleRepository.findByIdRole(dto.getIdRole())
                    .orElseThrow(() -> new RuntimeException("Role não encontrada"));
            usuario.setIdRole(role);

            Usuario atualizado = usuarioRepository.save(usuario);
            return ResponseEntity.ok(UsuarioMapper.toDTO(atualizado));
        }).orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {

        return usuarioRepository.findByCpf(cpf).map(usuario -> {
            usuarioRepository.delete(usuario);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
