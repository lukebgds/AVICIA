package com.avicia.api.data.mapper;


import com.avicia.api.data.dto.request.usuario.UsuarioRequest;
import com.avicia.api.data.dto.response.usuario.CriarUsuarioResponse;
import com.avicia.api.data.dto.response.usuario.UsuarioResponse;
import com.avicia.api.data.model.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequest dto){

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setSexo(dto.getSexo());
        usuario.setEstadoCivil(dto.getEstadoCivil());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setEndereco(dto.getEndereco());
        usuario.setMfaHabilitado(dto.getMfaHabilitado());
        usuario.setDataCriacao(dto.getDataCriacao());
        usuario.setAtivo(dto.getAtivo());

        return usuario;
    }

    

    public static UsuarioResponse toResponseDTO(Usuario usuario) {

        if (usuario == null) return null;

        UsuarioResponse dto = new UsuarioResponse();

        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf());
        dto.setDataNascimento(usuario.getDataNascimento());
        dto.setSexo(usuario.getSexo());
        dto.setEstadoCivil(usuario.getEstadoCivil());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setEndereco(usuario.getEndereco());
        dto.setAtivo(usuario.getAtivo());
        dto.setMfaHabilitado(usuario.getMfaHabilitado());
        dto.setDataCriacao(usuario.getDataCriacao());
        dto.setIdRole(usuario.getIdRole().getIdRole());

        return dto;
    }

    public static CriarUsuarioResponse toCriarResponseDTO(Usuario usuario) {
        if (usuario == null) return null;
        CriarUsuarioResponse dto = new CriarUsuarioResponse();
        dto.setIdUsuario(usuario.getIdUsuario());
        return dto;
    }

}
