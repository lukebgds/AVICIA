package com.avicia.api.features.sistema.systemLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.enumerate.TipoSystemLog;
import com.avicia.api.exception.SystemError;
import com.avicia.api.features.sistema.systemLog.request.SystemLogRequest;
import com.avicia.api.features.sistema.systemLog.response.EstatisticasLogsResponse;
import com.avicia.api.features.sistema.systemLog.response.SystemLogResponse;
import com.avicia.api.features.usuario.Usuario;
import com.avicia.api.features.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemLogService {
    
    private final SystemLogRepository systemLogRepository;
    private final UsuarioRepository usuarioRepository;
    private final VerificarSystemLog verificarSystemLog;
    private final SystemError systemError;
    
    @Transactional
    public SystemLogResponse registrar(SystemLogRequest dto) {
        // Validações
        verificarSystemLog.validarCamposObrigatorios(dto);
        
        SystemLog log = SystemLogMapper.toEntity(dto);
        
        // Recupera o usuário (se informado)
        if (dto.getIdUsuario() != null) {
            Usuario usuario = verificarSystemLog.verificarUsuarioExiste(dto.getIdUsuario());
            log.setIdUsuario(usuario);
        }
        
        // Define a data/hora atual caso não tenha sido informada
        if (dto.getDataHora() == null) {
            log.setDataHora(LocalDateTime.now());
        }
        
        SystemLog salvo = systemLogRepository.save(log);
        return SystemLogMapper.toResponseDTO(salvo);
    }
    
    @Transactional(readOnly = true)
    public List<SystemLogResponse> listarTodos() {
        return systemLogRepository.findAll()
            .stream()
            .map(SystemLogMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public SystemLogResponse buscarPorId(Integer id) {
        var log = systemLogRepository.findById(id)
            .orElse(null);
        if (log == null) {
            systemError.error("Log não encontrado com ID: %d", id);
    }

    return SystemLogMapper.toResponseDTO(log);
    }
    
    @Transactional(readOnly = true)
    public List<SystemLogResponse> buscarPorTipo(TipoSystemLog tipo) {
        verificarSystemLog.validarTipoLogNaoNulo(tipo);
        
        return systemLogRepository.findByTipoLog(tipo)
            .stream()
            .map(SystemLogMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<SystemLogResponse> buscarPorUsuario(Integer idUsuario) {
        verificarSystemLog.validarIdUsuarioNaoNulo(idUsuario);
        verificarSystemLog.verificarUsuarioExisteBoolean(idUsuario);
        
        return systemLogRepository.findByIdUsuario_IdUsuario(idUsuario)
            .stream()
            .map(SystemLogMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<SystemLogResponse> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        verificarSystemLog.validarPeriodoCompleto(dataInicio, dataFim);
        
        return systemLogRepository.findByDataHoraBetween(dataInicio, dataFim)
            .stream()
            .map(SystemLogMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<SystemLogResponse> buscarPorEntidade(String entidadeAfetada) {
        verificarSystemLog.validarEntidadeNaoVazia(entidadeAfetada);
        
        return systemLogRepository.findByEntidadeAfetada(entidadeAfetada)
            .stream()
            .map(SystemLogMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public int deletarLogsAntigos(LocalDateTime dataLimite) {
        verificarSystemLog.validarDataLimiteExclusao(dataLimite);
        
        List<SystemLog> logsAntigos = systemLogRepository.findByDataHoraBefore(dataLimite);
        int quantidade = logsAntigos.size();
        
        if (quantidade > 0) {
            systemLogRepository.deleteAll(logsAntigos);
            log.info("Deletados {} logs anteriores a {}", quantidade, dataLimite);
        }
        
        return quantidade;
    }
    
    private void registrarPadrao(Integer idUsuario, TipoSystemLog tipo, String acao,
                                 String entidadeAfetada, String detalhes) {
        try {
            SystemLogRequest logRequest = new SystemLogRequest();
            logRequest.setIdUsuario(idUsuario);
            logRequest.setTipoLog(tipo);
            logRequest.setAcao(acao);
            logRequest.setDataHora(LocalDateTime.now());
            logRequest.setEntidadeAfetada(entidadeAfetada);
            logRequest.setDetalhes(detalhes);
            
            registrar(logRequest);
            
        } catch (Exception e) {
            log.error("Falha ao registrar log [Tipo: {}, Ação: {}, Entidade: {}, Detalhes: {}]: {}", 
                     tipo, acao, entidadeAfetada, detalhes, e.getMessage(), e);
        }
    }
    
    @Transactional(readOnly = true)
    public EstatisticasLogsResponse obterEstatisticas() {
        List<SystemLog> todosLogs = systemLogRepository.findAll();
        
        if (todosLogs.isEmpty()) {
            return EstatisticasLogsResponse.builder()
                .totalLogs(0L)
                .totalCriacoes(0L)
                .totalModificacoes(0L)
                .totalExclusoes(0L)
                .totalErros(0L)
                .totalAvisos(0L)
                .usuarioMaisAtivo("Nenhum")
                .idUsuarioMaisAtivo(null)
                .acoesUsuarioMaisAtivo(0L)
                .entidadeMaisAfetada("Nenhuma")
                .operacoesEntidadeMaisAfetada(0L)
                .build();
        }
        
        long totalLogs = todosLogs.size();
        
        long totalCriacoes = todosLogs.stream()
            .filter(log -> log.getTipoLog() == TipoSystemLog.CRIACAO)
            .count();
        
        long totalModificacoes = todosLogs.stream()
            .filter(log -> log.getTipoLog() == TipoSystemLog.MODIFICACAO)
            .count();
        
        long totalExclusoes = todosLogs.stream()
            .filter(log -> log.getTipoLog() == TipoSystemLog.EXCLUSAO)
            .count();
        
        long totalErros = todosLogs.stream()
            .filter(log -> log.getTipoLog() == TipoSystemLog.ERRO)
            .count();
        
        long totalAvisos = todosLogs.stream()
            .filter(log -> log.getTipoLog() == TipoSystemLog.AVISO)
            .count();
        
        Map<Integer, Long> contagemPorUsuario = todosLogs.stream()
            .filter(log -> log.getIdUsuario() != null)
            .collect(Collectors.groupingBy(
                log -> log.getIdUsuario().getIdUsuario(),
                Collectors.counting()
            ));
        
        String usuarioMaisAtivo = "Sistema";
        Integer idUsuarioMaisAtivo = null;
        Long acoesUsuarioMaisAtivo = 0L;
        
        if (!contagemPorUsuario.isEmpty()) {
            Map.Entry<Integer, Long> usuarioTop = contagemPorUsuario.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
            
            if (usuarioTop != null) {
                idUsuarioMaisAtivo = usuarioTop.getKey();
                acoesUsuarioMaisAtivo = usuarioTop.getValue();
                
                Usuario usuario = usuarioRepository.findById(idUsuarioMaisAtivo)
                    .orElse(null);
                usuarioMaisAtivo = usuario != null ? usuario.getNome() : "ID: " + idUsuarioMaisAtivo;
            }
        }
        
        Map<String, Long> contagemPorEntidade = todosLogs.stream()
            .filter(log -> log.getEntidadeAfetada() != null)
            .collect(Collectors.groupingBy(
                SystemLog::getEntidadeAfetada,
                Collectors.counting()
            ));
        
        String entidadeMaisAfetada = "Nenhuma";
        Long operacoesEntidadeMaisAfetada = 0L;
        
        if (!contagemPorEntidade.isEmpty()) {
            Map.Entry<String, Long> entidadeTop = contagemPorEntidade.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
            
            if (entidadeTop != null) {
                entidadeMaisAfetada = entidadeTop.getKey();
                operacoesEntidadeMaisAfetada = entidadeTop.getValue();
            }
        }
        
        return EstatisticasLogsResponse.builder()
            .totalLogs(totalLogs)
            .totalCriacoes(totalCriacoes)
            .totalModificacoes(totalModificacoes)
            .totalExclusoes(totalExclusoes)
            .totalErros(totalErros)
            .totalAvisos(totalAvisos)
            .usuarioMaisAtivo(usuarioMaisAtivo)
            .idUsuarioMaisAtivo(idUsuarioMaisAtivo)
            .acoesUsuarioMaisAtivo(acoesUsuarioMaisAtivo)
            .entidadeMaisAfetada(entidadeMaisAfetada)
            .operacoesEntidadeMaisAfetada(operacoesEntidadeMaisAfetada)
            .build();
    }
    
    // ================= MÉTODOS AUXILIARES ================= //
    
    public void registrarCriacao(Integer idUsuario, String entidade, String detalhes) {
        if (entidade == null || entidade.trim().isEmpty()) {
            log.warn("Tentativa de registrar criação com entidade vazia");
            return;
        }
        registrarPadrao(idUsuario, TipoSystemLog.CRIACAO, "CRIAÇÃO: " + entidade, entidade, detalhes);
    }
    
    public void registrarAtualizacao(Integer idUsuario, String entidade, String detalhes) {
        if (entidade == null || entidade.trim().isEmpty()) {
            log.warn("Tentativa de registrar atualização com entidade vazia");
            return;
        }
        registrarPadrao(idUsuario, TipoSystemLog.MODIFICACAO, "MODIFICAÇÃO: " + entidade, entidade, detalhes);
    }
    
    public void registrarExclusao(Integer idUsuario, String entidade, String detalhes) {
        if (entidade == null || entidade.trim().isEmpty()) {
            log.warn("Tentativa de registrar exclusão com entidade vazia");
            return;
        }
        registrarPadrao(idUsuario, TipoSystemLog.EXCLUSAO, "EXCLUSÃO: " + entidade, entidade, detalhes);
    }
    
    public void registrarAviso(Integer idUsuario, String entidade, String detalhes) {
        if (entidade == null || entidade.trim().isEmpty()) {
            log.warn("Tentativa de registrar aviso com entidade vazia");
            return;
        }
        registrarPadrao(idUsuario, TipoSystemLog.AVISO, "AVISO: " + entidade, entidade, detalhes);
    }
    
    public void registrarErro(Integer idUsuario, String entidade, String detalhes) {
        if (entidade == null || entidade.trim().isEmpty()) {
            log.warn("Tentativa de registrar erro com entidade vazia");
            return;
        }
        registrarPadrao(idUsuario, TipoSystemLog.ERRO, "ERRO: " + entidade, entidade, detalhes);
    }
}