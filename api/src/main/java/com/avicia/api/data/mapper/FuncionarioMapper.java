package com.avicia.api.data.mapper;

import com.avicia.api.data.dto.object.FuncionarioDTO;
import com.avicia.api.data.dto.object.UsuarioDTO;
import com.avicia.api.model.Funcionario;
import com.avicia.api.model.Usuario;

public class FuncionarioMapper {

    public static FuncionarioDTO toDTO(Funcionario funcionario) {

        FuncionarioDTO dto = new FuncionarioDTO();

        dto.setIdAdministrativo(funcionario.getIdAdministrativo());
        dto.setCargo(funcionario.getCargo());
        dto.setSetor(funcionario.getSetor());
        dto.setMatricula(funcionario.getMatricula());
        dto.setObservacoes(funcionario.getObservacoes());

        Usuario usuario = funcionario.getUsuario();
        if (usuario != null) {
            UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuario);
            dto.setUsuario(usuarioDTO);
        }

        return dto;
    }

    public static Funcionario toEntity(FuncionarioDTO dto){

        Funcionario funcionario = new Funcionario();
        
        funcionario.setIdAdministrativo(dto.getIdAdministrativo());
        funcionario.setCargo(dto.getCargo());
        funcionario.setSetor(dto.getSetor());
        funcionario.setMatricula(dto.getMatricula());
        funcionario.setObservacoes(dto.getObservacoes());

        UsuarioDTO usuarioDTO = dto.getUsuario();
        if (usuarioDTO != null) {
            Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
            funcionario.setUsuario(usuario);
        }

        return funcionario;
    }

}
