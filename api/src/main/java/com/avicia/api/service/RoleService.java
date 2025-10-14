package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.object.RoleDTO;
import com.avicia.api.data.dto.request.RoleResquest;
import com.avicia.api.data.dto.response.role.CriarRoleResponse;
import com.avicia.api.data.dto.response.role.RoleResponse;
import com.avicia.api.data.mapper.RoleMapper;
import com.avicia.api.model.Role;
import com.avicia.api.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    @Autowired
    private final RoleRepository roleRepository;
    
    public CriarRoleResponse criar(RoleResquest dto) {

        Role role = RoleMapper.toEntity(dto);
        Role salvo = roleRepository.save(role);
        
        return RoleMapper.toCriarResponseDTO(salvo);
    }

    public List<RoleResponse> listarTodos() {

        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<RoleResponse> buscarPorNome(String nome) {
        return roleRepository.findByNome(nome).map(RoleMapper::toResponseDTO);
    }

    public Optional<RoleResponse> atualizar(String nome, RoleDTO dto) {
        
        return roleRepository.findByNome(nome).map(role -> {
            role.setNome(dto.getNome());
            role.setDescricao(dto.getDescricao());
            role.setPermissoes(dto.getPermissoes());

            Role atualizado = roleRepository.save(role);
            return RoleMapper.toResponseDTO(atualizado);
        });
    }

    public boolean deletar(String nome) {
        
        return roleRepository.findByNome(nome).map(role -> {
            roleRepository.delete(role);
            return true;
        }).orElse(false);
    }

}
