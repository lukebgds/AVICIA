package com.avicia.api.features.profissional;

import com.avicia.api.features.profissional.request.ProfissionalSaudeRequest;
import com.avicia.api.features.profissional.response.ProfissionalSaudeResponse;
import com.avicia.api.features.usuario.UsuarioMapper;

public class ProfissionalSaudeMapper {

	public static ProfissionalSaude toEntity (ProfissionalSaudeRequest dto) {
	
		ProfissionalSaude profissionalSaude = new ProfissionalSaude();
		
		// Talvez isso seja setado internamente
		profissionalSaude.setMatricula(dto.getMatricula());
		profissionalSaude.setConselho(dto.getConselho());
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
		dto.setConselho(profissionalSaude.getConselho());
		dto.setRegistroConselho(profissionalSaude.getRegistroConselho());
		dto.setEspecialidade(profissionalSaude.getEspecialidade());
		dto.setCargo(profissionalSaude.getCargo());
		dto.setUnidade(profissionalSaude.getUnidade());
		
		return dto;
	}

}