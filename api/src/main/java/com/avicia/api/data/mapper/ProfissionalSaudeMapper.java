package com.avicia.api.data.mapper;

import com.avicia.api.data.dto.request.ProfissionalSaudeRequest;
import com.avicia.api.data.dto.response.ProfissionalSaudeResponse;
import com.avicia.api.model.ProfissionalSaude;

public class ProfissionalSaudeMapper {

	public static ProfissionalSaude toEntity (ProfissionalSaudeRequest dto) {
	
		ProfissionalSaude profissionalSaude = new ProfissionalSaude();
		
		// Talvez isso seja setado internamente
		profissionalSaude.setMatricula(dto.getMatricula());
		profissionalSaude.setRegistroConselho(dto.getRegistroConselho());
		profissionalSaude.setEspecialidade(dto.getEspecialidade());
		profissionalSaude.setCargo(dto.getCargo());
		profissionalSaude.setUnidade(dto.getUnidade());

		return profissionalSaude;
	}
	
	public static ProfissionalSaudeResponse toResponseDTO (ProfissionalSaude profissionalSaude) {
	
		ProfissionalSaudeResponse dto = new ProfissionalSaudeResponse();
		
		dto.setIdProfissional(profissionalSaude.getIdProfissional());
		dto.setUsuario(UsuarioMapper.toResponseDTO(profissionalSaude.getUsuario()));
		dto.setMatricula(profissionalSaude.getMatricula());
		dto.setRegistroConselho(profissionalSaude.getRegistroConselho());
		dto.setEspecialidade(profissionalSaude.getEspecialidade());
		dto.setCargo(profissionalSaude.getCargo());
		dto.setUnidade(profissionalSaude.getUnidade());
		
		return dto;
	}

}