package com.avicia.api.data.mapper;

import com.avicia.api.data.dto.UsuarioDTO;
import com.avicia.api.model.Role;
import com.avicia.api.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {

        UsuarioDTO dto = new UsuarioDTO();

        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setSobrenome(usuario.getSobrenome());
        dto.setCpf(usuario.getCpf());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setAtivo(usuario.getAtivo());
        dto.setMfaHabilitado(usuario.getMfaHabilitado());
        dto.setDataCriacao(usuario.getDataCriacao());
        dto.setIdRole(usuario.getIdRole().getIdRole());

        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto){

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setAtivo(dto.getAtivo());
        usuario.setAtivo(dto.getMfaHabilitado());
        usuario.setDataCriacao(dto.getDataCriacao());

        return usuario;
    }

}
