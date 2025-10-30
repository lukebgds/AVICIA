package com.avicia.api.features.paciente.dados.diagnostico;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.enumerate.StatusDiagnostico;
import com.avicia.api.data.enumerate.TipoDiagnostico;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.dados.diagnostico.response.PacienteDiagnosticoResponse;
import com.avicia.api.features.paciente.dados.diagnostico.resquest.PacienteDiagnosticoRequest;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteDiagnosticoService {

    private final PacienteDiagnosticoRepository pacienteDiagnosticoRepository;
    private final SystemLogService systemLogService;
    private final VerificarPacienteDiagnostico verificarPacienteDiagnostico;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<PacienteDiagnosticoResponse> listarTodos() {
        return pacienteDiagnosticoRepository.findAll()
                .stream()
                .map(PacienteDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteDiagnosticoResponse> listarPorPaciente(Integer idPaciente) {
        verificarPacienteDiagnostico.validarIdPacienteNaoNulo(idPaciente);
        return pacienteDiagnosticoRepository.findByPaciente_IdPaciente(idPaciente)
                .stream()
                .map(PacienteDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteDiagnosticoResponse> listarPorPacienteECodigoCid(Integer idPaciente, String codigoCidDez) {
        verificarPacienteDiagnostico.validarIdPacienteNaoNulo(idPaciente);
        return pacienteDiagnosticoRepository.findByPaciente_IdPacienteAndCodigoCidDez(idPaciente, codigoCidDez)
                .stream()
                .map(PacienteDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteDiagnosticoResponse> listarPorPacienteETipo(Integer idPaciente, TipoDiagnostico tipo) {
        verificarPacienteDiagnostico.validarIdPacienteNaoNulo(idPaciente);
        return pacienteDiagnosticoRepository.findByPaciente_IdPacienteAndTipo(idPaciente, tipo)
                .stream()
                .map(PacienteDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteDiagnosticoResponse> listarPorPacienteEStatus(Integer idPaciente, StatusDiagnostico status) {
        verificarPacienteDiagnostico.validarIdPacienteNaoNulo(idPaciente);
        return pacienteDiagnosticoRepository.findByPaciente_IdPacienteAndStatus(idPaciente, status)
                .stream()
                .map(PacienteDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteDiagnosticoResponse> listarPorPacienteEPeriodo(
            Integer idPaciente, 
            LocalDate dataInicio, 
            LocalDate dataFim) {
        verificarPacienteDiagnostico.validarIdPacienteNaoNulo(idPaciente);
        return pacienteDiagnosticoRepository.findByPaciente_IdPacienteAndDataDiagnosticoBetween(
                idPaciente, dataInicio, dataFim)
                .stream()
                .map(PacienteDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteDiagnosticoResponse> listarPorPacienteTipoEStatus(
            Integer idPaciente, 
            TipoDiagnostico tipo, 
            StatusDiagnostico status) {
        verificarPacienteDiagnostico.validarIdPacienteNaoNulo(idPaciente);
        return pacienteDiagnosticoRepository.findByPaciente_IdPacienteAndTipoAndStatus(idPaciente, tipo, status)
                .stream()
                .map(PacienteDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PacienteDiagnosticoResponse buscarPorId(Integer id) {
        PacienteDiagnostico diagnostico = verificarPacienteDiagnostico.buscarDiagnosticoPorId(id);
        return PacienteDiagnosticoMapper.toResponseDTO(diagnostico);
    }

    @Transactional
    public PacienteDiagnosticoResponse criar(PacienteDiagnosticoRequest dto) {
        // Validações
        verificarPacienteDiagnostico.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteDiagnostico.validarCodigoCidDezNaoVazio(dto.getCodigoCidDez());
        verificarPacienteDiagnostico.validarDataDiagnosticoNaoNula(dto.getDataDiagnostico());
        verificarPacienteDiagnostico.validarTipoDiagnosticoNaoNulo(dto.getTipo());
        verificarPacienteDiagnostico.validarStatusDiagnosticoNaoNulo(dto.getStatus());
        
        PacienteDiagnostico diagnostico = PacienteDiagnosticoMapper.toEntity(dto);

        // Recupera o paciente
        Paciente paciente = verificarPacienteDiagnostico.buscarPacientePorId(dto.getIdPaciente());
        
        diagnostico.setPaciente(paciente);

        PacienteDiagnostico salvo = pacienteDiagnosticoRepository.save(diagnostico);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteDiagnostico",
            "Diagnóstico criado com ID " + salvo.getIdDiagnostico() + " para paciente: " + paciente.getIdPaciente()
        );

        return PacienteDiagnosticoMapper.toResponseDTO(salvo);
    }

    @Transactional
    public PacienteDiagnosticoResponse atualizar(Integer id, PacienteDiagnosticoRequest dto) {
        verificarPacienteDiagnostico.validarIdDiagnosticoNaoNulo(id);
        verificarPacienteDiagnostico.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteDiagnostico.validarCodigoCidDezNaoVazio(dto.getCodigoCidDez());
        verificarPacienteDiagnostico.validarDataDiagnosticoNaoNula(dto.getDataDiagnostico());
        verificarPacienteDiagnostico.validarTipoDiagnosticoNaoNulo(dto.getTipo());
        verificarPacienteDiagnostico.validarStatusDiagnosticoNaoNulo(dto.getStatus());
        
        PacienteDiagnostico existing = verificarPacienteDiagnostico.buscarDiagnosticoPorId(id);
        
        // Verifica se o paciente existe
        Paciente paciente = verificarPacienteDiagnostico.buscarPacientePorId(dto.getIdPaciente());
        
        PacienteDiagnostico diagnostico = PacienteDiagnosticoMapper.toEntity(dto);
        diagnostico.setIdDiagnostico(existing.getIdDiagnostico());
        diagnostico.setPaciente(paciente);
        
        PacienteDiagnostico atualizado = pacienteDiagnosticoRepository.save(diagnostico);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteDiagnostico",
            "Dados do diagnóstico com ID " + id + " foram atualizados"
        );
        
        return PacienteDiagnosticoMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarPacienteDiagnostico.validarIdDiagnosticoNaoNulo(id);
        
        PacienteDiagnostico existing = verificarPacienteDiagnostico.buscarDiagnosticoPorId(id);
        
        pacienteDiagnosticoRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteDiagnostico",
            "Diagnóstico com ID " + id + " foi deletado"
        );
    }

}
