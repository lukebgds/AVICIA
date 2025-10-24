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
import com.avicia.api.security.verify.VerificarProfissionalSaude;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfissionalSaudeService {
    
    private final ProfissionalSaudeRepository profissionalRepository;
    private final SystemLogService systemLogService;
    private final VerificarProfissionalSaude verificarProfissionalSaude;
    
    @Transactional
    public ProfissionalSaudeResponse criar(ProfissionalSaudeRequest dto) {
        // Validações
        verificarProfissionalSaude.validarIdUsuarioNaoNulo(dto.getIdUsuario());
        verificarProfissionalSaude.validarMatriculaNaoVazia(dto.getMatricula());
        verificarProfissionalSaude.validarRegistroConselhoNaoVazio(dto.getRegistroConselho());
        
        // Verifica duplicidades
        verificarProfissionalSaude.verificarMatriculaDuplicada(dto.getMatricula());
        verificarProfissionalSaude.verificarRegistroConselhoDuplicado(dto.getRegistroConselho());
        
        ProfissionalSaude profissionalSaude = ProfissionalSaudeMapper.toEntity(dto);
        
        // Recupera o usuário
        Usuario usuario = verificarProfissionalSaude.buscarUsuarioPorId(dto.getIdUsuario());
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
    
    @Transactional(readOnly = true)
    public List<ProfissionalSaudeResponse> listarTodos() {
        return profissionalRepository.findAll()
                .stream()
                .map(ProfissionalSaudeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ProfissionalSaudeResponse buscarPorIdProfissional(Integer idProfissional) {
        ProfissionalSaude profissional = verificarProfissionalSaude.buscarProfissionalPorId(idProfissional);
        return ProfissionalSaudeMapper.toResponseDTO(profissional);
    }
    
    @Transactional(readOnly = true)
    public ProfissionalSaudeResponse buscarPorMatricula(String matricula) {
        ProfissionalSaude profissional = verificarProfissionalSaude.buscarProfissionalPorMatricula(matricula);
        return ProfissionalSaudeMapper.toResponseDTO(profissional);
    }
    
    @Transactional(readOnly = true)
    public ProfissionalSaudeResponse buscarPorRegistroConselho(String registroConselho) {
        ProfissionalSaude profissional = verificarProfissionalSaude.buscarProfissionalPorRegistroConselho(registroConselho);
        return ProfissionalSaudeMapper.toResponseDTO(profissional);
    }
    
    @Transactional
    public ProfissionalSaudeResponse atualizar(String matricula, ProfissionalSaudeRequest dto) {
        verificarProfissionalSaude.validarMatriculaNaoVazia(matricula);
        verificarProfissionalSaude.validarIdUsuarioNaoNulo(dto.getIdUsuario());
        
        ProfissionalSaude profissionalSaude = verificarProfissionalSaude.buscarProfissionalPorMatricula(matricula);
        
        // Verifica se a nova matrícula já existe (se for diferente da atual)
        verificarProfissionalSaude.verificarMatriculaDuplicadaAtualizacao(
            matricula, 
            dto.getMatricula(), 
            profissionalSaude.getIdProfissional()
        );
        
        // Verifica se o novo registro do conselho já existe (se for diferente do atual)
        verificarProfissionalSaude.verificarRegistroConselhoDuplicadoAtualizacao(
            profissionalSaude.getRegistroConselho(),
            dto.getRegistroConselho(),
            profissionalSaude.getIdProfissional()
        );
        
        profissionalSaude.setMatricula(dto.getMatricula());
        profissionalSaude.setRegistroConselho(dto.getRegistroConselho());
        profissionalSaude.setEspecialidade(dto.getEspecialidade());
        profissionalSaude.setCargo(dto.getCargo());
        profissionalSaude.setUnidade(dto.getUnidade());
        
        // Atualiza o usuário vinculado
        Usuario usuario = verificarProfissionalSaude.buscarUsuarioPorIdAtualizacao(
            dto.getIdUsuario(), 
            profissionalSaude.getIdProfissional()
        );
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
        verificarProfissionalSaude.validarMatriculaNaoVazia(matricula);
        
        ProfissionalSaude profissionalSaude = verificarProfissionalSaude.buscarProfissionalPorMatricula(matricula);
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
        
        verificarProfissionalSaude.validarIdUsuarioParaGeracao(idUsuario);
        
        String idUsuarioStr = String.valueOf(idUsuario);
        String prefixo = idUsuarioStr.substring(0, 3);
        
        while (tentativas < maxTentativas) {
            String numeroAleatorio = String.format("%03d", new Random().nextInt(1_000));
            Integer idProfissional = Integer.parseInt(prefixo + numeroAleatorio);
            
            if (!verificarProfissionalSaude.idProfissionalExiste(idProfissional)) {
                return idProfissional;
            }
            tentativas++;
        }
        
        // Se chegou aqui, não conseguiu gerar ID único após todas as tentativas
        throw new BusinessException(
            100,
            "ProfissionalSaude",
            "Não foi possível gerar um ID único para o profissional de saúde após %d tentativas. Tente novamente.",
            maxTentativas
        );
    }
}