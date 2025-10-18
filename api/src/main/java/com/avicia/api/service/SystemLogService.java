package com.avicia.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.dto.request.system.SystemLogRequest;
import com.avicia.api.data.dto.response.system.SystemLogResponse;
import com.avicia.api.data.dto.response.system.EstatisticasLogsResponse;
import com.avicia.api.data.enumerate.TipoSystemLog;
import com.avicia.api.data.mapper.SystemLogMapper;
import com.avicia.api.exception.BusinessException;
import com.avicia.api.model.SystemLog;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.SystemLogRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemLogService {
    
    private final SystemLogRepository systemLogRepository;
    private final UsuarioRepository usuarioRepository;
    
    // Registrar logs internamente.
    @Transactional
    public SystemLogResponse registrar(SystemLogRequest dto) {
        
        // Validações
        if (dto == null) {
            throw new BusinessException("Request de log não pode ser nulo");
        }
        
        if (dto.getTipoLog() == null) {
            throw new BusinessException("Tipo de log não pode ser nulo");
        }
        
        if (dto.getAcao() == null || dto.getAcao().trim().isEmpty()) {
            throw new BusinessException("Ação do log não pode ser vazia");
        }
        
        if (dto.getEntidadeAfetada() == null || dto.getEntidadeAfetada().trim().isEmpty()) {
            throw new BusinessException("Entidade afetada não pode ser vazia");
        }
        
        try {
            SystemLog log = SystemLogMapper.toEntity(dto);
            
            // Recupera o usuário (se informado)
            if (dto.getIdUsuario() != null) {
                Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                        .orElseThrow(() -> new BusinessException(
                            "Usuário com ID %d não encontrado para registro de log", 
                            dto.getIdUsuario()
                        ));
                log.setIdUsuario(usuario);
            }
            
            // Define a data/hora atual caso não tenha sido informada
            if (dto.getDataHora() == null) {
                log.setDataHora(LocalDateTime.now());
            }
            
            SystemLog salvo = systemLogRepository.save(log);
            return SystemLogMapper.toResponseDTO(salvo);
            
        } catch (BusinessException e) {
            // Re-lança BusinessException
            throw e;
        } catch (Exception e) {
            // Log para console em caso de erro crítico ao salvar o log
            log.error("Erro crítico ao tentar salvar log no banco de dados: {}", e.getMessage(), e);
            throw new BusinessException("Erro ao registrar log no sistema: %s", e.getMessage());
        }
    }
    
    // Lista todos os logs.
    @Transactional(readOnly = true)
    public List<SystemLogResponse> listarTodos() {
        try {
            return systemLogRepository.findAll()
                .stream()
                .map(SystemLogMapper::toResponseDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao listar todos os logs: {}", e.getMessage(), e);
            throw new BusinessException("Erro ao listar logs do sistema");
        }
    }
    
    // Busca log por ID.
    @Transactional(readOnly = true)
    public SystemLogResponse buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("ID do log não pode ser nulo");
        }
        
        return systemLogRepository.findById(id)
            .map(SystemLogMapper::toResponseDTO)
            .orElseThrow(() -> new BusinessException("Log com ID %d não encontrado", id));
    }
    
    // Busca logs por tipo
    @Transactional(readOnly = true)
    public List<SystemLogResponse> buscarPorTipo(TipoSystemLog tipo) {
        if (tipo == null) {
            throw new BusinessException("Tipo de log não pode ser nulo");
        }
        
        try {
            return systemLogRepository.findByTipoLog(tipo)
                .stream()
                .map(SystemLogMapper::toResponseDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao buscar logs por tipo {}: {}", tipo, e.getMessage(), e);
            throw new BusinessException("Erro ao buscar logs por tipo");
        }
    }
    
    // Busca logs por usuário
    @Transactional(readOnly = true)
    public List<SystemLogResponse> buscarPorUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new BusinessException("ID do usuário não pode ser nulo");
        }
        
        // Verifica se o usuário existe
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new BusinessException("Usuário com ID %d não encontrado", idUsuario);
        }
        
        try {
            return systemLogRepository.findByIdUsuario_IdUsuario(idUsuario)
                .stream()
                .map(SystemLogMapper::toResponseDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao buscar logs do usuário {}: {}", idUsuario, e.getMessage(), e);
            throw new BusinessException("Erro ao buscar logs do usuário");
        }
    }
    
    // Busca logs por período
    @Transactional(readOnly = true)
    public List<SystemLogResponse> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (dataInicio == null) {
            throw new BusinessException("Data de início não pode ser nula");
        }
        
        if (dataFim == null) {
            throw new BusinessException("Data de fim não pode ser nula");
        }
        
        if (dataInicio.isAfter(dataFim)) {
            throw new BusinessException("Data de início não pode ser posterior à data de fim");
        }
        
        try {
            return systemLogRepository.findByDataHoraBetween(dataInicio, dataFim)
                .stream()
                .map(SystemLogMapper::toResponseDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao buscar logs por período: {}", e.getMessage(), e);
            throw new BusinessException("Erro ao buscar logs por período");
        }
    }
    
    // Busca logs por entidade
    @Transactional(readOnly = true)
    public List<SystemLogResponse> buscarPorEntidade(String entidadeAfetada) {
        if (entidadeAfetada == null || entidadeAfetada.trim().isEmpty()) {
            throw new BusinessException("Entidade afetada não pode ser vazia");
        }
        
        try {
            return systemLogRepository.findByEntidadeAfetada(entidadeAfetada)
                .stream()
                .map(SystemLogMapper::toResponseDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao buscar logs da entidade {}: {}", entidadeAfetada, e.getMessage(), e);
            throw new BusinessException("Erro ao buscar logs da entidade");
        }
    }
    
    // Deleta logs antigos (útil para limpeza periódica)
    @Transactional
    public int deletarLogsAntigos(LocalDateTime dataLimite) {
        if (dataLimite == null) {
            throw new BusinessException("Data limite não pode ser nula");
        }
        
        if (dataLimite.isAfter(LocalDateTime.now())) {
            throw new BusinessException("Data limite não pode ser no futuro");
        }
        
        try {
            List<SystemLog> logsAntigos = systemLogRepository.findByDataHoraBefore(dataLimite);
            int quantidade = logsAntigos.size();
            
            systemLogRepository.deleteAll(logsAntigos);
            
            log.info("Deletados {} logs anteriores a {}", quantidade, dataLimite);
            
            return quantidade;
        } catch (Exception e) {
            log.error("Erro ao deletar logs antigos: {}", e.getMessage(), e);
            throw new BusinessException("Erro ao deletar logs antigos");
        }
    }
    
    // Modelo padrão de Registro - com tratamento de erro silencioso
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
            // Não lança exceção para não interromper o fluxo principal
            // Apenas registra no console/arquivo de log
            log.error("Falha ao registrar log [Tipo: {}, Ação: {}, Entidade: {}, Detalhes: {}]: {}", 
                     tipo, acao, entidadeAfetada, detalhes, e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public EstatisticasLogsResponse obterEstatisticas() {
        try {
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
            
            // Total de logs
            long totalLogs = todosLogs.size();
            
            // Contagem por tipo
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
            
            // Usuário mais ativo
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
                    
                    // Busca o nome do usuário
                    usuarioRepository.findById(idUsuarioMaisAtivo).ifPresent(usuario -> {
                        // Usa uma variável final para o lambda
                        final String nomeUsuario = usuario.getNome();
                    });
                    
                    Usuario usuario = usuarioRepository.findById(idUsuarioMaisAtivo).orElse(null);
                    usuarioMaisAtivo = usuario != null ? usuario.getNome() : "ID: " + idUsuarioMaisAtivo;
                }
            }
            
            // Entidade mais afetada
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
                
        } catch (Exception e) {
            log.error("Erro ao obter estatísticas de logs: {}", e.getMessage(), e);
            throw new BusinessException("Erro ao obter estatísticas de logs");
        }
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