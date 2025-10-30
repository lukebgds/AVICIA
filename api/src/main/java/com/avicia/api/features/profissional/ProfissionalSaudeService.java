package com.avicia.api.features.profissional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.exception.BusinessException;
import com.avicia.api.features.profissional.request.ProfissionalSaudeRequest;
import com.avicia.api.features.profissional.response.ProfissionalSaudeResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.features.usuario.Usuario;

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

        // Encontrar ID de Usuário pelo Token de Sessão
        Integer id = verificarProfissionalSaude.getIdUsuarioToken();
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            id,
            "Profissional de Saúde",
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

        // Encontrar ID de Usuário pelo Token de Sessão
        Integer id = verificarProfissionalSaude.getIdUsuarioToken();
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            id,
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

        // Encontrar ID de Usuário pelo Token de Sessão
        Integer id = verificarProfissionalSaude.getIdUsuarioToken();
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            id,
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

        // Encontrar ID de Usuário pelo Token de Sessão
        Integer id = verificarProfissionalSaude.getIdUsuarioToken();
        
        // Se chegou aqui, não conseguiu gerar ID único após todas as tentativas
        throw new BusinessException(
            id,
            "Profissional de Saúde",
            "Não foi possível gerar um ID único para o profissional de saúde após %d tentativas. Tente novamente.",
            maxTentativas
        );
    }
}