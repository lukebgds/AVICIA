package com.avicia.api.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.dto.request.profissional.ProfissionalSaudeRequest;
import com.avicia.api.data.dto.response.profissional.ProfissionalSaudeResponse;
import com.avicia.api.data.mapper.ProfissionalSaudeMapper;
import com.avicia.api.exception.BusinessException;
import com.avicia.api.model.ProfissionalSaude;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.ProfissionalSaudeRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfissionalSaudeService {
    
    private final ProfissionalSaudeRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemLogService systemLogService;
    
    @Transactional
    public ProfissionalSaudeResponse criar(ProfissionalSaudeRequest dto) {
        // Validações
        if (dto.getIdUsuario() == null) {
            throw new BusinessException("ID do usuário não pode ser nulo");
        }
        
        if (dto.getMatricula() == null || dto.getMatricula().trim().isEmpty()) {
            throw new BusinessException("Matrícula não pode ser vazia");
        }
        
        if (dto.getRegistroConselho() == null || dto.getRegistroConselho().trim().isEmpty()) {
            throw new BusinessException("Registro do conselho não pode ser vazio");
        }
        
        // Verifica se a matrícula já existe
        if (profissionalRepository.findByMatricula(dto.getMatricula()).isPresent()) {
            systemLogService.registrarErro(
                null,
                "ProfissionalSaude",
                "Tentativa de criar profissional com matrícula duplicada: " + dto.getMatricula()
            );
            throw new BusinessException("Já existe um profissional cadastrado com a matrícula %s", dto.getMatricula());
        }
        
        // Verifica se o registro do conselho já existe
        if (profissionalRepository.findByRegistroConselho(dto.getRegistroConselho()).isPresent()) {
            systemLogService.registrarErro(
                null,
                "ProfissionalSaude",
                "Tentativa de criar profissional com registro de conselho duplicado: " + dto.getRegistroConselho()
            );
            throw new BusinessException("Já existe um profissional cadastrado com o registro de conselho %s", dto.getRegistroConselho());
        }
        
        ProfissionalSaude profissionalSaude = ProfissionalSaudeMapper.toEntity(dto);
        
        // Recupera o usuário
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> {
                    systemLogService.registrarErro(
                        null,
                        "ProfissionalSaude",
                        "Tentativa de criar profissional com usuário inexistente: ID " + dto.getIdUsuario()
                    );
                    return new BusinessException("Usuário com ID %d não encontrado", dto.getIdUsuario());
                });
        
        profissionalSaude.setUsuario(usuario);
        
        // Geração do ID do Profissional de Saúde
        Integer idProfissional = gerarIdUnicoProfissional(usuario.getIdUsuario());
        profissionalSaude.setIdProfissional(idProfissional);
        
        ProfissionalSaude salvo = profissionalRepository.save(profissionalSaude);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            salvo.getIdProfissional(),
            "ProfissionalSaude",
            "Profissional de saúde criado com ID " + salvo.getIdProfissional() + " e matrícula " + salvo.getMatricula()
        );
        
        return ProfissionalSaudeMapper.toResponseDTO(salvo);
    }
    
    @Transactional
    public List<ProfissionalSaudeResponse> listarTodos() {
        return profissionalRepository.findAll()
                .stream()
                .map(ProfissionalSaudeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public ProfissionalSaudeResponse buscarPorIdProfissional(Integer idProfissional) {
        if (idProfissional == null) {
            throw new BusinessException("ID do profissional não pode ser nulo");
        }
        
        return profissionalRepository.findByIdProfissional(idProfissional)
                .map(ProfissionalSaudeMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Profissional de saúde com ID %d não encontrado", idProfissional));
    }
    
    @Transactional
    public ProfissionalSaudeResponse buscarPorMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new BusinessException("Matrícula não pode ser vazia");
        }
        
        return profissionalRepository.findByMatricula(matricula)
                .map(ProfissionalSaudeMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Profissional de saúde com matrícula %s não encontrado", matricula));
    }
    
    @Transactional
    public ProfissionalSaudeResponse buscarPorRegistroConselho(String registroConselho) {
        if (registroConselho == null || registroConselho.trim().isEmpty()) {
            throw new BusinessException("Registro do conselho não pode ser vazio");
        }
        
        return profissionalRepository.findByRegistroConselho(registroConselho)
                .map(ProfissionalSaudeMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Profissional de saúde com registro de conselho %s não encontrado", registroConselho));
    }
    
    @Transactional
    public ProfissionalSaudeResponse atualizar(String matricula, ProfissionalSaudeRequest dto) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new BusinessException("Matrícula não pode ser vazia");
        }
        
        if (dto.getIdUsuario() == null) {
            throw new BusinessException("ID do usuário não pode ser nulo");
        }
        
        ProfissionalSaude profissionalSaude = profissionalRepository.findByMatricula(matricula)
                .orElseThrow(() -> new BusinessException("Profissional de saúde com matrícula %s não encontrado", matricula));
        
        // Verifica se a nova matrícula já existe (se for diferente da atual)
        if (!dto.getMatricula().equals(matricula) && profissionalRepository.findByMatricula(dto.getMatricula()).isPresent()) {
            systemLogService.registrarErro(
                profissionalSaude.getIdProfissional(),
                "ProfissionalSaude",
                "Tentativa de atualizar para matrícula duplicada: " + dto.getMatricula()
            );
            throw new BusinessException("Já existe um profissional cadastrado com a matrícula %s", dto.getMatricula());
        }
        
        // Verifica se o novo registro do conselho já existe (se for diferente do atual)
        if (!dto.getRegistroConselho().equals(profissionalSaude.getRegistroConselho()) && 
            profissionalRepository.findByRegistroConselho(dto.getRegistroConselho()).isPresent()) {
            systemLogService.registrarErro(
                profissionalSaude.getIdProfissional(),
                "ProfissionalSaude",
                "Tentativa de atualizar para registro de conselho duplicado: " + dto.getRegistroConselho()
            );
            throw new BusinessException("Já existe um profissional cadastrado com o registro de conselho %s", dto.getRegistroConselho());
        }
        
        profissionalSaude.setMatricula(dto.getMatricula());
        profissionalSaude.setRegistroConselho(dto.getRegistroConselho());
        profissionalSaude.setEspecialidade(dto.getEspecialidade());
        profissionalSaude.setCargo(dto.getCargo());
        profissionalSaude.setUnidade(dto.getUnidade());
        
        // Atualiza o usuário vinculado
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> {
                    systemLogService.registrarErro(
                        profissionalSaude.getIdProfissional(),
                        "ProfissionalSaude",
                        "Tentativa de atualizar profissional com usuário inexistente: ID " + dto.getIdUsuario()
                    );
                    return new BusinessException("Usuário com ID %d não encontrado", dto.getIdUsuario());
                });
        
        profissionalSaude.setUsuario(usuario);
        
        ProfissionalSaude atualizado = profissionalRepository.save(profissionalSaude);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            atualizado.getIdProfissional(),
            "ProfissionalSaude",
            "Dados do profissional de saúde com matrícula " + matricula + " foram atualizados"
        );
        
        return ProfissionalSaudeMapper.toResponseDTO(atualizado);
    }
    
    @Transactional
    public void deletar(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new BusinessException("Matrícula não pode ser vazia");
        }
        
        ProfissionalSaude profissionalSaude = profissionalRepository.findByMatricula(matricula)
                .orElseThrow(() -> new BusinessException("Profissional de saúde com matrícula %s não encontrado", matricula));
        
        Integer idProfissional = profissionalSaude.getIdProfissional();
        
        profissionalRepository.delete(profissionalSaude);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            idProfissional,
            "ProfissionalSaude",
            "Profissional de saúde com matrícula " + matricula + " foi deletado"
        );
    }
    
    // ================= MÉTODOS AUXILIARES ================= //
    
    private Integer gerarIdUnicoProfissional(Integer idUsuario) {
        int tentativas = 0;
        int maxTentativas = 1000;
        
        String idUsuarioStr = String.valueOf(idUsuario);
        
        if (idUsuarioStr.length() < 3) {
            throw new BusinessException("ID do usuário inválido para geração de ID do profissional");
        }
        
        String prefixo = idUsuarioStr.substring(0, 3);
        
        while (tentativas < maxTentativas) {
            String numeroAleatorio = String.format("%03d", new Random().nextInt(1_000));
            Integer idProfissional = Integer.parseInt(prefixo + numeroAleatorio);
            
            if (!profissionalRepository.existsById(idProfissional)) {
                return idProfissional;
            }
            tentativas++;
        }
        
        systemLogService.registrarErro(
            null,
            "ProfissionalSaude",
            "Não foi possível gerar ID único para profissional após " + maxTentativas + " tentativas"
        );
        throw new BusinessException("Não foi possível gerar um ID único para o profissional de saúde. Tente novamente.");
    }
}