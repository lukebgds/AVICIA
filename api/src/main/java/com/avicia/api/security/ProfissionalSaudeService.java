package com.avicia.api.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.object.ProfissionalSaudeDTO;
import com.avicia.api.data.mapper.ProfissionalSaudeMapper;
import com.avicia.api.model.ProfissionalSaude;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.ProfissionalSaudeRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfissionalSaudeService {

    private final ProfissionalSaudeRepository profissionalRepository;

    private final UsuarioRepository usuarioRepository;

    public ProfissionalSaudeDTO criar(ProfissionalSaudeDTO dto) {

        ProfissionalSaude profissional = ProfissionalSaudeMapper.toEntity(dto);

        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        profissional.setUsuario(usuario);

        ProfissionalSaude salvo = profissionalRepository.save(profissional);

        return ProfissionalSaudeMapper.toDTO(salvo);
    }

    public List<ProfissionalSaudeDTO> listarTodos() {

        return profissionalRepository.findAll()
                .stream()
                .map(ProfissionalSaudeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProfissionalSaudeDTO> buscarPorId(Integer idProfissional) {

        return profissionalRepository.findById(idProfissional)
                .map(ProfissionalSaudeMapper::toDTO);
    }

    public Optional<ProfissionalSaudeDTO> buscarPorMatricula(String matricula) {

        return profissionalRepository.findByMatricula(matricula)
                .map(ProfissionalSaudeMapper::toDTO);
    }

    public Optional<ProfissionalSaudeDTO> buscarPorRegistroConselho(String registroConselho) {

        return profissionalRepository.findByRegistroConselho(registroConselho)
                .map(ProfissionalSaudeMapper::toDTO);
    }

    public Optional<ProfissionalSaudeDTO> atualizar(Integer idProfissional, ProfissionalSaudeDTO dto) {

        return profissionalRepository.findById(idProfissional).map(profissional -> {
            profissional.setMatricula(dto.getMatricula());
            profissional.setRegistroConselho(dto.getRegistroConselho());
            profissional.setEspecialidade(dto.getEspecialidade());
            profissional.setCargo(dto.getCargo());
            profissional.setUnidade(dto.getUnidade());

            Usuario usuario = usuarioRepository.findById(dto.getUsuario().getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            profissional.setUsuario(usuario);

            ProfissionalSaude atualizado = profissionalRepository.save(profissional);

            return ProfissionalSaudeMapper.toDTO(atualizado);
        });
    }

    public boolean deletar(Integer idProfissional) {
        
        return profissionalRepository.findById(idProfissional).map(profissional -> {
            profissionalRepository.delete(profissional);
            return true;
        }).orElse(false);
    }

}
