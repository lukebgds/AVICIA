package com.avicia.api.features.clinico.exame.solicitado;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.clinico.consulta.Consulta;
import com.avicia.api.features.clinico.exame.Exame;
import com.avicia.api.features.clinico.exame.solicitado.request.ExameSolicitadoRequest;
import com.avicia.api.features.clinico.exame.solicitado.response.ExameSolicitadoResponse;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExameSolicitadoService {

    private final ExameSolicitadoRepository exameSolicitadoRepository;
    private final SystemLogService systemLogService;
    private final VerificarExameSolicitado verificarExameSolicitado;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<ExameSolicitadoResponse> listarTodos() {
        return exameSolicitadoRepository.findAll()
                .stream()
                .map(ExameSolicitadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameSolicitadoResponse> listarPorConsulta(Integer idConsulta) {
        return exameSolicitadoRepository.findByConsulta_IdConsulta(idConsulta)
                .stream()
                .map(ExameSolicitadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameSolicitadoResponse> listarPorPaciente(Integer idPaciente) {
        verificarExameSolicitado.validarIdPacienteNaoNulo(idPaciente);
        return exameSolicitadoRepository.findByPaciente_IdPaciente(idPaciente)
                .stream()
                .map(ExameSolicitadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameSolicitadoResponse> listarPorPacienteOrdenado(Integer idPaciente) {
        verificarExameSolicitado.validarIdPacienteNaoNulo(idPaciente);
        return exameSolicitadoRepository.findByPaciente_IdPacienteOrderByDataSolicitacaoDesc(idPaciente)
                .stream()
                .map(ExameSolicitadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameSolicitadoResponse> listarPorProfissional(Integer idProfissional) {
        verificarExameSolicitado.validarIdProfissionalNaoNulo(idProfissional);
        return exameSolicitadoRepository.findByProfissionalSaude_IdProfissional(idProfissional)
                .stream()
                .map(ExameSolicitadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameSolicitadoResponse> listarPorTipoExame(Integer idExame) {
        verificarExameSolicitado.validarIdExameNaoNulo(idExame);
        return exameSolicitadoRepository.findByExame_IdExame(idExame)
                .stream()
                .map(ExameSolicitadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameSolicitadoResponse> listarPorStatus(String status) {
        return exameSolicitadoRepository.findByStatus(status)
                .stream()
                .map(ExameSolicitadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameSolicitadoResponse> listarPorPacienteEStatus(Integer idPaciente, String status) {
        verificarExameSolicitado.validarIdPacienteNaoNulo(idPaciente);
        return exameSolicitadoRepository.findByPaciente_IdPacienteAndStatus(idPaciente, status)
                .stream()
                .map(ExameSolicitadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameSolicitadoResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return exameSolicitadoRepository.findByDataSolicitacaoBetween(dataInicio, dataFim)
                .stream()
                .map(ExameSolicitadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExameSolicitadoResponse> listarPorPacienteEPeriodo(
            Integer idPaciente, 
            LocalDate dataInicio, 
            LocalDate dataFim) {
        verificarExameSolicitado.validarIdPacienteNaoNulo(idPaciente);
        return exameSolicitadoRepository.findByPaciente_IdPacienteAndDataSolicitacaoBetween(
                idPaciente, dataInicio, dataFim)
                .stream()
                .map(ExameSolicitadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExameSolicitadoResponse buscarPorId(Integer id) {
        ExameSolicitado exameSolicitado = verificarExameSolicitado.buscarExameSolicitadoPorId(id);
        return ExameSolicitadoMapper.toResponseDTO(exameSolicitado);
    }

    @Transactional
    public ExameSolicitadoResponse criar(ExameSolicitadoRequest dto) {
        // Validações
        verificarExameSolicitado.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarExameSolicitado.validarIdExameNaoNulo(dto.getIdExame());
        verificarExameSolicitado.validarIdProfissionalNaoNulo(dto.getIdProfissionalSaude());
        verificarExameSolicitado.validarDataSolicitacaoNaoNula(dto.getDataSolicitacao());
        verificarExameSolicitado.validarStatusNaoVazio(dto.getStatus());
        
        ExameSolicitado exameSolicitado = ExameSolicitadoMapper.toEntity(dto);

        // Recupera as entidades relacionadas
        Paciente paciente = verificarExameSolicitado.buscarPacientePorId(dto.getIdPaciente());
        Exame exame = verificarExameSolicitado.buscarExamePorId(dto.getIdExame());
        ProfissionalSaude profissional = verificarExameSolicitado.buscarProfissionalPorId(dto.getIdProfissionalSaude());
        
        exameSolicitado.setPaciente(paciente);
        exameSolicitado.setExame(exame);
        exameSolicitado.setProfissionalSaude(profissional);
        
        // Se houver consulta, vincula
        if (dto.getIdConsulta() != null) {
            Consulta consulta = verificarExameSolicitado.buscarConsultaPorId(dto.getIdConsulta());
            exameSolicitado.setConsulta(consulta);
        }

        ExameSolicitado salvo = exameSolicitadoRepository.save(exameSolicitado);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ExameSolicitado",
            "Exame solicitado com ID " + salvo.getIdExameSolicitado() + 
            " - " + exame.getNome() + " para paciente: " + paciente.getIdPaciente()
        );

        return ExameSolicitadoMapper.toResponseDTO(salvo);
    }

    @Transactional
    public ExameSolicitadoResponse atualizar(Integer id, ExameSolicitadoRequest dto) {
        verificarExameSolicitado.validarIdExameSolicitadoNaoNulo(id);
        verificarExameSolicitado.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarExameSolicitado.validarIdExameNaoNulo(dto.getIdExame());
        verificarExameSolicitado.validarIdProfissionalNaoNulo(dto.getIdProfissionalSaude());
        verificarExameSolicitado.validarDataSolicitacaoNaoNula(dto.getDataSolicitacao());
        verificarExameSolicitado.validarStatusNaoVazio(dto.getStatus());
        
        ExameSolicitado existing = verificarExameSolicitado.buscarExameSolicitadoPorId(id);
        
        // Verifica as entidades relacionadas
        Paciente paciente = verificarExameSolicitado.buscarPacientePorId(dto.getIdPaciente());
        Exame exame = verificarExameSolicitado.buscarExamePorId(dto.getIdExame());
        ProfissionalSaude profissional = verificarExameSolicitado.buscarProfissionalPorId(dto.getIdProfissionalSaude());
        
        ExameSolicitado exameSolicitado = ExameSolicitadoMapper.toEntity(dto);
        exameSolicitado.setIdExameSolicitado(existing.getIdExameSolicitado());
        exameSolicitado.setPaciente(paciente);
        exameSolicitado.setExame(exame);
        exameSolicitado.setProfissionalSaude(profissional);
        
        // Se houver consulta, vincula
        if (dto.getIdConsulta() != null) {
            Consulta consulta = verificarExameSolicitado.buscarConsultaPorId(dto.getIdConsulta());
            exameSolicitado.setConsulta(consulta);
        }
        
        ExameSolicitado atualizado = exameSolicitadoRepository.save(exameSolicitado);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ExameSolicitado",
            "Dados do exame solicitado com ID " + id + " foram atualizados"
        );
        
        return ExameSolicitadoMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public ExameSolicitadoResponse atualizarStatus(Integer id, String novoStatus) {
        verificarExameSolicitado.validarIdExameSolicitadoNaoNulo(id);
        verificarExameSolicitado.validarStatusNaoVazio(novoStatus);
        
        ExameSolicitado exameSolicitado = verificarExameSolicitado.buscarExameSolicitadoPorId(id);
        exameSolicitado.setStatus(novoStatus);
        
        ExameSolicitado atualizado = exameSolicitadoRepository.save(exameSolicitado);
        
        // Registro de log
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ExameSolicitado",
            "Status do exame solicitado ID " + id + " alterado para: " + novoStatus
        );
        
        return ExameSolicitadoMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarExameSolicitado.validarIdExameSolicitadoNaoNulo(id);
        
        ExameSolicitado existing = verificarExameSolicitado.buscarExameSolicitadoPorId(id);
        
        exameSolicitadoRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ExameSolicitado",
            "Exame solicitado com ID " + id + " foi deletado"
        );
    }

}
