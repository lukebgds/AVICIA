package com.avicia.api.features.clinico.exame.resultado;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.clinico.exame.resultado.request.ExameResultadoRequest;
import com.avicia.api.features.clinico.exame.resultado.response.ExameResultadoResponse;
import com.avicia.api.features.clinico.exame.solicitado.ExameSolicitado;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExameResultadoService {

    private final ExameResultadoRepository exameResultadoRepository;
    private final SystemLogService systemLogService;
    private final VerificarExameResultado verificarExameResultado;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<ExameResultadoResponse> listarTodos() {
        return exameResultadoRepository.findAll().stream()
                .map(ExameResultadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExameResultadoResponse buscarPorExameSolicitado(Integer idExameSolicitado) {
        verificarExameResultado.validarIdExameSolicitadoNaoNulo(idExameSolicitado);
        ExameResultado resultado = exameResultadoRepository
                .findByExameSolicitado_IdExameSolicitado(idExameSolicitado)
                .orElse(null);
        return ExameResultadoMapper.toResponseDTO(resultado);
    }

    @Transactional(readOnly = true)
    public List<ExameResultadoResponse> listarPorPaciente(Integer idPaciente) {
        return exameResultadoRepository.findByExameSolicitado_Paciente_IdPaciente(idPaciente).stream()
                .map(ExameResultadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResultadoResponse> listarPorPacienteOrdenado(Integer idPaciente) {
        return exameResultadoRepository
                .findByExameSolicitado_Paciente_IdPacienteOrderByDataResultadoDesc(idPaciente).stream()
                .map(ExameResultadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResultadoResponse> listarPorProfissional(Integer idProfissional) {
        return exameResultadoRepository.findByAssinadoPor_IdProfissional(idProfissional).stream()
                .map(ExameResultadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResultadoResponse> listarPorStatus(String status) {
        return exameResultadoRepository.findByStatus(status).stream()
                .map(ExameResultadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResultadoResponse> listarPorPacienteEStatus(Integer idPaciente, String status) {
        return exameResultadoRepository
                .findByExameSolicitado_Paciente_IdPacienteAndStatus(idPaciente, status).stream()
                .map(ExameResultadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResultadoResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return exameResultadoRepository.findByDataResultadoBetween(dataInicio, dataFim).stream()
                .map(ExameResultadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameResultadoResponse> listarPendentesAssinatura() {
        return exameResultadoRepository.findByAssinadoPorIsNull().stream()
                .map(ExameResultadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExameResultadoResponse buscarPorId(Integer id) {
        ExameResultado resultado = verificarExameResultado.buscarResultadoPorId(id);
        return ExameResultadoMapper.toResponseDTO(resultado);
    }

    @Transactional
    public ExameResultadoResponse criar(ExameResultadoRequest dto) {
        verificarExameResultado.validarIdExameSolicitadoNaoNulo(dto.getIdExameSolicitado());
        verificarExameResultado.validarDataResultadoNaoNula(dto.getDataResultado());
        verificarExameResultado.validarStatusNaoVazio(dto.getStatus());
        
        ExameResultado resultado = ExameResultadoMapper.toEntity(dto);
        ExameSolicitado exameSolicitado = verificarExameResultado
                .buscarExameSolicitadoPorId(dto.getIdExameSolicitado());
        
        resultado.setExameSolicitado(exameSolicitado);
        
        if (dto.getIdAssinadoPor() != null) {
            ProfissionalSaude profissional = verificarExameResultado
                    .buscarProfissionalPorId(dto.getIdAssinadoPor());
            resultado.setAssinadoPor(profissional);
        }

        ExameResultado salvo = exameResultadoRepository.save(resultado);
        
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ExameResultado",
            "Resultado criado com ID " + salvo.getIdResultado() + 
            " para exame: " + exameSolicitado.getExame().getNome()
        );

        return ExameResultadoMapper.toResponseDTO(salvo);
    }

    @Transactional
    public ExameResultadoResponse atualizar(Integer id, ExameResultadoRequest dto) {
        verificarExameResultado.validarIdResultadoNaoNulo(id);
        verificarExameResultado.validarIdExameSolicitadoNaoNulo(dto.getIdExameSolicitado());
        verificarExameResultado.validarDataResultadoNaoNula(dto.getDataResultado());
        verificarExameResultado.validarStatusNaoVazio(dto.getStatus());
        
        ExameResultado existing = verificarExameResultado.buscarResultadoPorId(id);
        ExameSolicitado exameSolicitado = verificarExameResultado
                .buscarExameSolicitadoPorId(dto.getIdExameSolicitado());
        
        ExameResultado resultado = ExameResultadoMapper.toEntity(dto);
        resultado.setIdResultado(existing.getIdResultado());
        resultado.setExameSolicitado(exameSolicitado);
        
        if (dto.getIdAssinadoPor() != null) {
            ProfissionalSaude profissional = verificarExameResultado
                    .buscarProfissionalPorId(dto.getIdAssinadoPor());
            resultado.setAssinadoPor(profissional);
        }
        
        ExameResultado atualizado = exameResultadoRepository.save(resultado);
        
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ExameResultado",
            "Dados do resultado com ID " + id + " foram atualizados"
        );
        
        return ExameResultadoMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public ExameResultadoResponse assinar(Integer id, Integer idProfissional) {
        verificarExameResultado.validarIdResultadoNaoNulo(id);
        
        ExameResultado resultado = verificarExameResultado.buscarResultadoPorId(id);
        ProfissionalSaude profissional = verificarExameResultado.buscarProfissionalPorId(idProfissional);
        
        resultado.setAssinadoPor(profissional);
        resultado.setStatus("ASSINADO");
        
        ExameResultado atualizado = exameResultadoRepository.save(resultado);
        
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ExameResultado",
            "Resultado ID " + id + " assinado por profissional ID " + idProfissional
        );
        
        return ExameResultadoMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarExameResultado.validarIdResultadoNaoNulo(id);
        ExameResultado existing = verificarExameResultado.buscarResultadoPorId(id);
        exameResultadoRepository.delete(existing);
        
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ExameResultado",
            "Resultado com ID " + id + " foi deletado"
        );
    }

}
