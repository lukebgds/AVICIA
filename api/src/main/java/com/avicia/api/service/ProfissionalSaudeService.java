package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.request.ProfissionalSaudeRequest;
import com.avicia.api.data.dto.response.ProfissionalSaudeResponse;
import com.avicia.api.data.mapper.ProfissionalSaudeMapper;
import com.avicia.api.model.ProfissionalSaude;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.ProfissionalSaudeRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfissionalSaudeService {

	@Autowired
    private final ProfissionalSaudeRepository profissionalRepository;

	@Autowired
    private final UsuarioRepository usuarioRepository;

	public ProfissionalSaudeResponse criar(ProfissionalSaudeRequest dto) {
		
		ProfissionalSaude profissionalSaude = ProfissionalSaudeMapper.toEntity(dto);
		
		// Recupera o usuário
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        profissionalSaude.setUsuario(usuario);
		
		// Geração do ID do Profissinal de Saúde:
        String idUsuarioStr = String.valueOf(usuario.getIdUsuario());
		
		// Pega os 3 primeiros dígitos
        String prefixo = idUsuarioStr.substring(0, 3);

        // Número aleatório de 3 dígitos
        String numeroAleatorio = String.format("%03d", new Random().nextInt(1000));

        // Concatena e converte para Integer
        Integer idProfissional = Integer.parseInt(prefixo + numeroAleatorio);
		
		// Garante unicidade
        while (profissionalRepository.existsById(idProfissional)) {
            numeroAleatorio = String.format("%03d", new Random().nextInt(1000));
            idProfissional = Integer.parseInt(prefixo + numeroAleatorio);
        }
		
		profissionalSaude.setIdProfissional(idProfissional);
		ProfissionalSaude salvo = profissionalRepository.save(profissionalSaude);

        return ProfissionalSaudeMapper.toResponseDTO(salvo);
		
	}
	
	public List<ProfissionalSaudeResponse> listarTodos() {

        return profissionalRepository.findAll()
                .stream()
                .map(ProfissionalSaudeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
	
	public Optional<ProfissionalSaudeResponse> buscarPorIdProfissional(Integer idProfissional) {

        return profissionalRepository.findByIdProfissional(idProfissional)
                .map(ProfissionalSaudeMapper::toResponseDTO);
    }
	
	public Optional<ProfissionalSaudeResponse> buscarPorMatricula(String matricula) {

        return profissionalRepository.findByMatricula(matricula)
                .map(ProfissionalSaudeMapper::toResponseDTO);
    }
	
	public Optional<ProfissionalSaudeResponse> buscarPorRegistroConselho(String registroConselho) {
		
		return profissionalRepository.findByRegistroConselho(registroConselho)
			.map(ProfissionalSaudeMapper::toResponseDTO);
	}
    
	public Optional<ProfissionalSaudeResponse> atualizar(String matricula, ProfissionalSaudeRequest dto) {

        return profissionalRepository.findByMatricula(matricula).map(profissionalSaude -> {
            profissionalSaude.setMatricula(dto.getMatricula());
			profissionalSaude.setRegistroConselho(dto.getRegistroConselho());
			profissionalSaude.setEspecialidade(dto.getEspecialidade());
			profissionalSaude.setCargo(dto.getCargo());
			profissionalSaude.setUnidade(dto.getUnidade());

            // Atualiza o usuário vinculado
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            profissionalSaude.setUsuario(usuario);

            ProfissionalSaude atualizado = profissionalRepository.save(profissionalSaude);
            return ProfissionalSaudeMapper.toResponseDTO(atualizado);
        });
    }
	
	public boolean deletar(String matricula) {
        
        return profissionalRepository.findByMatricula(matricula).map(profissionalSaude -> {
            profissionalRepository.delete(profissionalSaude);
            return true;
        }).orElse(false);
    }

}
