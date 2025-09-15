package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.UsuarioDTO;
import com.avicia.api.data.mapper.UsuarioMapper;
import com.avicia.api.model.Role;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.RoleRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;

    public UsuarioDTO criar(UsuarioDTO dto) {
        
        Usuario usuario = UsuarioMapper.toEntity(dto);

        // Verifica se a role do usuário existe
        Role role = roleRepository.findByIdRole(dto.getIdRole())
            .orElseThrow(() -> new RuntimeException("Role não Encontrada"));
        usuario.setIdRole(role);    

        Usuario salvo = usuarioRepository.save(usuario);

        return UsuarioMapper.toDTO(salvo);
    }

    public List<UsuarioDTO> listarTodos() {
        
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscaPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf).map(UsuarioMapper::toDTO);
    }

    public Optional<UsuarioDTO> atualizar(String cpf, UsuarioDTO dto) {
        return usuarioRepository.findByCpf(cpf).map(usuario -> {
            usuario.setNome(dto.getNome());
            usuario.setSobrenome(dto.getSobrenome());
            usuario.setCpf(dto.getCpf());
            usuario.setEmail(dto.getEmail());
            usuario.setTelefone(dto.getTelefone());
            usuario.setAtivo(dto.getAtivo());
            usuario.setMfaHabilitado(dto.getMfaHabilitado());
            usuario.setDataCriacao(dto.getDataCriacao());

            // Verifica se a role do usuário existe
            Role role = roleRepository.findByIdRole(dto.getIdRole())
                    .orElseThrow(() -> new RuntimeException("Role não encontrada"));
            usuario.setIdRole(role);

            Usuario atualizado = usuarioRepository.save(usuario);
            return UsuarioMapper.toDTO(atualizado);
        });
    }

    public boolean deletar(String cpf) {
        return usuarioRepository.findByCpf(cpf).map(usuario -> {
            usuarioRepository.delete(usuario);
            return true;
        }).orElse(false);
    }
}
