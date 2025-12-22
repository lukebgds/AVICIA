package com.avicia.api.features.clinico.exame;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.clinico.exame.request.ExameRequest;
import com.avicia.api.features.clinico.exame.response.ExameResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExameService {

    private final ExameRepository exameRepository;
    private final SystemLogService systemLogService;
    private final VerificarExame verificarExame;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<ExameResponse> listarTodos() {
        return exameRepository.findAll()
                .stream()
                .map(ExameMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResponse> listarAtivos() {
        return exameRepository.findByAtivo(true)
                .stream()
                .map(ExameMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResponse> listarAtivosOrdenados() {
        return exameRepository.findByAtivoOrderByNomeAsc(true)
                .stream()
                .map(ExameMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResponse> listarPorStatus(Boolean ativo) {
        return exameRepository.findByAtivo(ativo)
                .stream()
                .map(ExameMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResponse> buscarPorNome(String nome) {
        return exameRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(ExameMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResponse> listarPorTipo(String tipo) {
        return exameRepository.findByTipo(tipo)
                .stream()
                .map(ExameMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResponse> listarPorTipoOrdenado(String tipo) {
        return exameRepository.findByTipoOrderByNomeAsc(tipo)
                .stream()
                .map(ExameMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResponse> listarPorTipoEStatus(String tipo, Boolean ativo) {
        return exameRepository.findByTipoAndAtivo(tipo, ativo)
                .stream()
                .map(ExameMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExameResponse buscarPorId(Integer id) {
        Exame exame = verificarExame.buscarExamePorId(id);
        return ExameMapper.toResponseDTO(exame);
    }

    @Transactional
    public ExameResponse criar(ExameRequest dto) {
        // Validações
        verificarExame.validarNomeExameNaoVazio(dto.getNome());
        verificarExame.validarTipoExameNaoVazio(dto.getTipo());
        verificarExame.validarAtivoNaoNulo(dto.getAtivo());
        
        Exame exame = ExameMapper.toEntity(dto);

        Exame salvo = exameRepository.save(exame);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Exame",
            "Exame criado com ID " + salvo.getIdExame() + ": " + salvo.getNome()
        );

        return ExameMapper.toResponseDTO(salvo);
    }

    @Transactional
    public ExameResponse atualizar(Integer id, ExameRequest dto) {
        verificarExame.validarIdExameNaoNulo(id);
        verificarExame.validarNomeExameNaoVazio(dto.getNome());
        verificarExame.validarTipoExameNaoVazio(dto.getTipo());
        verificarExame.validarAtivoNaoNulo(dto.getAtivo());
        
        Exame existing = verificarExame.buscarExamePorId(id);
        
        Exame exame = ExameMapper.toEntity(dto);
        exame.setIdExame(existing.getIdExame());
        
        Exame atualizado = exameRepository.save(exame);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Exame",
            "Dados do exame com ID " + id + " foram atualizados"
        );
        
        return ExameMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarExame.validarIdExameNaoNulo(id);
        
        Exame existing = verificarExame.buscarExamePorId(id);
        
        exameRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Exame",
            "Exame com ID " + id + " foi deletado"
        );
    }

    @Transactional
    public ExameResponse ativar(Integer id) {
        verificarExame.validarIdExameNaoNulo(id);
        
        Exame exame = verificarExame.buscarExamePorId(id);
        exame.setAtivo(true);
        
        Exame atualizado = exameRepository.save(exame);
        
        // Registro de log
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Exame",
            "Exame com ID " + id + " foi ativado"
        );
        
        return ExameMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public ExameResponse desativar(Integer id) {
        verificarExame.validarIdExameNaoNulo(id);
        
        Exame exame = verificarExame.buscarExamePorId(id);
        exame.setAtivo(false);
        
        Exame atualizado = exameRepository.save(exame);
        
        // Registro de log
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Exame",
            "Exame com ID " + id + " foi desativado"
        );
        
        return ExameMapper.toResponseDTO(atualizado);
    }

}
