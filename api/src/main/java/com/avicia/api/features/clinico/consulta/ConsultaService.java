package com.avicia.api.features.clinico.consulta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.enumerate.TipoConsulta;
import com.avicia.api.features.clinico.consulta.request.ConsultaRequest;
import com.avicia.api.features.clinico.consulta.response.ConsultaResponse;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final SystemLogService systemLogService;
    private final VerificarConsulta verificarConsulta;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarTodos() {
        return consultaRepository.findAll()
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorPaciente(Integer idPaciente) {
        verificarConsulta.validarIdPacienteNaoNulo(idPaciente);
        return consultaRepository.findByPaciente_IdPaciente(idPaciente)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorPacienteOrdenado(Integer idPaciente) {
        verificarConsulta.validarIdPacienteNaoNulo(idPaciente);
        return consultaRepository.findByPaciente_IdPacienteOrderByDataConsultaDesc(idPaciente)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorProfissionalSaude(Integer idProfissionalSaude) {
        verificarConsulta.validarIdProfissionalSaudeNaoNulo(idProfissionalSaude);
        return consultaRepository.findByProfissionalSaude_IdProfissional(idProfissionalSaude)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorProfissionalSaudeOrdenado(Integer idProfissionalSaude) {
        verificarConsulta.validarIdProfissionalSaudeNaoNulo(idProfissionalSaude);
        return consultaRepository.findByProfissionalSaude_IdProfissionalOrderByDataConsultaDesc(
                idProfissionalSaude)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorTipo(TipoConsulta tipoConsulta) {
        return consultaRepository.findByTipoConsulta(tipoConsulta)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorPacienteETipo(Integer idPaciente, TipoConsulta tipoConsulta) {
        verificarConsulta.validarIdPacienteNaoNulo(idPaciente);
        return consultaRepository.findByPaciente_IdPacienteAndTipoConsulta(idPaciente, tipoConsulta)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorProfissionalETipo(
            Integer idProfissionalSaude, 
            TipoConsulta tipoConsulta) {
        verificarConsulta.validarIdProfissionalSaudeNaoNulo(idProfissionalSaude);
        return consultaRepository.findByProfissionalSaude_IdProfissionalAndTipoConsulta(
                idProfissionalSaude, tipoConsulta)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return consultaRepository.findByDataConsultaBetween(dataInicio, dataFim)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorPacienteEPeriodo(
            Integer idPaciente, 
            LocalDateTime dataInicio, 
            LocalDateTime dataFim) {
        verificarConsulta.validarIdPacienteNaoNulo(idPaciente);
        return consultaRepository.findByPaciente_IdPacienteAndDataConsultaBetween(
                idPaciente, dataInicio, dataFim)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorProfissionalEPeriodo(
            Integer idProfissionalSaude, 
            LocalDateTime dataInicio, 
            LocalDateTime dataFim) {
        verificarConsulta.validarIdProfissionalSaudeNaoNulo(idProfissionalSaude);
        return consultaRepository.findByProfissionalSaude_IdProfissionalAndDataConsultaBetween(
                idProfissionalSaude, dataInicio, dataFim)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> buscarPorLocal(String localConsulta) {
        return consultaRepository.findByLocalConsultaContainingIgnoreCase(localConsulta)
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConsultaResponse buscarPorId(Integer id) {
        Consulta consulta = verificarConsulta.buscarConsultaPorId(id);
        return ConsultaMapper.toResponseDTO(consulta);
    }

    @Transactional
    public ConsultaResponse criar(ConsultaRequest dto) {
        // Validações
        verificarConsulta.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarConsulta.validarIdProfissionalSaudeNaoNulo(dto.getIdProfissionalSaude());
        verificarConsulta.validarDataConsultaNaoNula(dto.getDataConsulta());
        verificarConsulta.validarTipoConsultaNaoNulo(dto.getTipoConsulta());
        
        Consulta consulta = ConsultaMapper.toEntity(dto);

        // Recupera o paciente
        Paciente paciente = verificarConsulta.buscarPacientePorId(dto.getIdPaciente());
        
        // Recupera o profissional de saúde
        ProfissionalSaude profissional = verificarConsulta.buscarProfissionalSaudePorId(dto.getIdProfissionalSaude());
        
        consulta.setPaciente(paciente);
        consulta.setProfissionalSaude(profissional);

        Consulta salva = consultaRepository.save(consulta);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Consulta",
            "Consulta criada com ID " + salva.getIdConsulta() + " para paciente: " + 
            paciente.getIdPaciente() + " e profissional: " + profissional.getIdProfissional()
        );

        return ConsultaMapper.toResponseDTO(salva);
    }

    @Transactional
    public ConsultaResponse atualizar(Integer id, ConsultaRequest dto) {
        verificarConsulta.validarIdConsultaNaoNulo(id);
        verificarConsulta.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarConsulta.validarIdProfissionalSaudeNaoNulo(dto.getIdProfissionalSaude());
        verificarConsulta.validarDataConsultaNaoNula(dto.getDataConsulta());
        verificarConsulta.validarTipoConsultaNaoNulo(dto.getTipoConsulta());
        
        Consulta existing = verificarConsulta.buscarConsultaPorId(id);
        
        // Verifica se o paciente existe
        Paciente paciente = verificarConsulta.buscarPacientePorId(dto.getIdPaciente());
        
        // Verifica se o profissional de saúde existe
        ProfissionalSaude profissional = verificarConsulta.buscarProfissionalSaudePorId(dto.getIdProfissionalSaude());
        
        Consulta consulta = ConsultaMapper.toEntity(dto);
        consulta.setIdConsulta(existing.getIdConsulta());
        consulta.setPaciente(paciente);
        consulta.setProfissionalSaude(profissional);
        
        Consulta atualizada = consultaRepository.save(consulta);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Consulta",
            "Dados da consulta com ID " + id + " foram atualizados"
        );
        
        return ConsultaMapper.toResponseDTO(atualizada);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarConsulta.validarIdConsultaNaoNulo(id);
        
        Consulta existing = verificarConsulta.buscarConsultaPorId(id);
        
        consultaRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Consulta",
            "Consulta com ID " + id + " foi deletada"
        );
    }

}
