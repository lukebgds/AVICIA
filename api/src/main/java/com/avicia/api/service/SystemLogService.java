package com.avicia.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.dto.request.system.SystemLogRequest;
import com.avicia.api.data.dto.response.system.SystemLogResponse;
import com.avicia.api.data.enumerate.TipoSystemLog;
import com.avicia.api.data.mapper.SystemLogMapper;
import com.avicia.api.data.model.SystemLog;
import com.avicia.api.data.model.Usuario;
import com.avicia.api.repository.SystemLogRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemLogService {

    private final SystemLogRepository systemLogRepository;
    private final UsuarioRepository usuarioRepository;

    // Registrar logs internamente.
    @Transactional
    public SystemLogResponse registrar(SystemLogRequest dto) {

        SystemLog log = SystemLogMapper.toEntity(dto);

        // Recupera o usuário (se informado)
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado para log."));
            log.setIdUsuario(usuario);
        }

        // Define a data/hora atual caso não tenha sido informada
        if (dto.getDataHora() == null) {
            log.setDataHora(LocalDateTime.now());
        }

        SystemLog salvo = systemLogRepository.save(log);
        return SystemLogMapper.toResponseDTO(salvo);
    }

    // Lista todos os logs.
    public List<SystemLogResponse> listarTodos() {
        return systemLogRepository.findAll()
            .stream()
            .map(SystemLogMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    
    // Busca log por ID.
    public Optional<SystemLogResponse> buscarPorId(Integer id) {
        return systemLogRepository.findById(id)
            .map(SystemLogMapper::toResponseDTO);
    }

    // Modelo padrão de Registro.
    private void registrarPadrao(Integer idUsuario, TipoSystemLog tipo, String acao,
                                 String entidadeAfetada, String detalhes) {
        SystemLogRequest log = new SystemLogRequest();
        log.setIdUsuario(idUsuario);
        log.setTipoLog(tipo);
        log.setAcao(acao);
        log.setDataHora(LocalDateTime.now());
        log.setEntidadeAfetada(entidadeAfetada);
        log.setDetalhes(detalhes);

        registrar(log);
    }

    // ======================================================
    // MÉTODOS AUXILIARES
    // ======================================================
    public void registrarCriacao(Integer idUsuario, String entidade, String detalhes) {
        registrarPadrao(idUsuario, TipoSystemLog.CRIACAO, "Criação de " + entidade, entidade, detalhes);
    }

    public void registrarAtualizacao(Integer idUsuario, String entidade, String detalhes) {
        registrarPadrao(idUsuario, TipoSystemLog.MODIFICACAO, "Atualização de " + entidade, entidade, detalhes);
    }

    public void registrarExclusao(Integer idUsuario, String entidade, String detalhes) {
        registrarPadrao(idUsuario, TipoSystemLog.EXCLUSAO, "Exclusão de " + entidade, entidade, detalhes);
    }

    public void registrarAviso(Integer idUsuario, String entidade, String detalhes) {
        registrarPadrao(idUsuario, TipoSystemLog.AVISO, "Aviso sobre " + entidade, entidade, detalhes);
    }

    public void registrarErro(Integer idUsuario, String entidade, String detalhes) {
        registrarPadrao(idUsuario, TipoSystemLog.ERRO, "Erro em " + entidade, entidade, detalhes);
    }

}
