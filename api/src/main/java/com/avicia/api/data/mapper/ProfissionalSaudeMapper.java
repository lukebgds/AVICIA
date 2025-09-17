package com.avicia.api.data.mapper;

import com.avicia.api.data.dto.object.ProfissionalSaudeDTO;
import com.avicia.api.data.dto.object.UsuarioDTO;
import com.avicia.api.model.ProfissionalSaude;
import com.avicia.api.model.Usuario;

public class ProfissionalSaudeMapper {

    public static ProfissionalSaudeDTO toDTO(ProfissionalSaude profissional) {

        ProfissionalSaudeDTO dto = new ProfissionalSaudeDTO();

        dto.setIdProfissional(profissional.getIdProfissional());
        dto.setMatricula(profissional.getMatricula());
        dto.setRegistroConselho(profissional.getRegistroConselho());
        dto.setEspecialidade(profissional.getEspecialidade());
        dto.setCargo(profissional.getCargo());
        dto.setUnidade(profissional.getUnidade());

        Usuario usuario = profissional.getUsuario();
        if (usuario != null) {
            UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuario);
            dto.setUsuario(usuarioDTO);
        }

        return dto;
    }

    public static ProfissionalSaude toEntity(ProfissionalSaudeDTO dto) {

        ProfissionalSaude profissional = new ProfissionalSaude();

        profissional.setIdProfissional(dto.getIdProfissional());
        profissional.setMatricula(dto.getMatricula());
        profissional.setRegistroConselho(dto.getRegistroConselho());
        profissional.setEspecialidade(dto.getEspecialidade());
        profissional.setCargo(dto.getCargo());
        profissional.setUnidade(dto.getUnidade());

        UsuarioDTO usuarioDTO = dto.getUsuario();
        if (usuarioDTO != null) {
            Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
            profissional.setUsuario(usuario);
        }

        return profissional;
    }

    

}
