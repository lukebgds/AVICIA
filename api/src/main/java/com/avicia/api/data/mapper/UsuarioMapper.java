package com.avicia.api.data.mapper;


import com.avicia.api.data.dto.request.UsuarioRequest;
import com.avicia.api.data.dto.response.UsuarioResponse;
import com.avicia.api.model.Usuario;

public class UsuarioMapper {

    public static UsuarioResponse toResponseDTO(Usuario usuario) {

        if (usuario == null) return null;

        UsuarioResponse dto = new UsuarioResponse();

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

    public static Usuario toEntity(UsuarioRequest dto){

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setAtivo(dto.getAtivo());
        usuario.setMfaHabilitado(dto.getMfaHabilitado());
        usuario.setDataCriacao(dto.getDataCriacao());

        return usuario;
    }

}
