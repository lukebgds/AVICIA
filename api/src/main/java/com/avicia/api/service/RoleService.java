package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.object.RoleDTO;
import com.avicia.api.data.mapper.RoleMapper;
import com.avicia.api.model.Role;
import com.avicia.api.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    
    public RoleDTO criar(RoleDTO dto) {

        Role role = RoleMapper.toEntity(dto);
        Role salvo = roleRepository.save(role);
        
        return RoleMapper.toDTO(salvo);
    }

    public List<RoleDTO> listarTodos() {

        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<RoleDTO> buscarPorNome(String nome) {
        return roleRepository.findByNome(nome).map(RoleMapper::toDTO);
    }

    public Optional<RoleDTO> atualizar(String nome, RoleDTO dto) {
        
        return roleRepository.findByNome(nome).map(role -> {
            role.setNome(dto.getNome());
            role.setDescricao(dto.getDescricao());
            role.setPermissoes(dto.getPermissoes());

            Role atualizado = roleRepository.save(role);
            return RoleMapper.toDTO(atualizado);
        });
    }

    public boolean deletar(String nome) {
        
        return roleRepository.findByNome(nome).map(role -> {
            roleRepository.delete(role);
            return true;
        }).orElse(false);
    }

}
