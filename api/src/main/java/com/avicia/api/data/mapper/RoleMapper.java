package com.avicia.api.data.mapper;

import com.avicia.api.data.dto.RoleDTO;
import com.avicia.api.model.Role;

public class RoleMapper {

    public static RoleDTO toDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setIdRole(role.getIdRole());
        dto.setNome(role.getNome());
        dto.setDescricao(role.getDescricao());
        dto.setPermissoes(role.getPermissoes());
        return dto;
    }

    public static Role toEntity(RoleDTO dto) {
        Role role = new Role();
        role.setIdRole(dto.getIdRole());
        role.setNome(dto.getNome());
        role.setDescricao(dto.getDescricao());
        role.setPermissoes(dto.getPermissoes());
        return role;
    }

}
