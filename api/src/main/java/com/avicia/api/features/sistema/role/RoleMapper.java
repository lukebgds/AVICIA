package com.avicia.api.features.sistema.role;

import com.avicia.api.features.sistema.role.request.RoleRequest;
import com.avicia.api.features.sistema.role.response.CriarRoleResponse;
import com.avicia.api.features.sistema.role.response.RoleResponse;

public class RoleMapper {

    public static Role toEntity(RoleRequest dto, Integer id) {
        Role role = new Role();
        role.setIdRole(id);
        role.setNome(dto.getNome());
        
        role.setDescricao(dto.getDescricao());
        role.setPermissoes(dto.getPermissoes());
        return role;
    }

    public static RoleResponse toResponseDTO(Role role) {
        RoleResponse dto = new RoleResponse();
        dto.setIdRole(role.getIdRole());
        dto.setNome(role.getNome());
        dto.setDescricao(role.getDescricao());
        dto.setPermissoes(role.getPermissoes());
        return dto;
    }

    public static CriarRoleResponse toCriarResponseDTO(Role role) {
        CriarRoleResponse dto = new CriarRoleResponse();
        dto.setIdRole(role.getIdRole());
        return dto;
    }


}
