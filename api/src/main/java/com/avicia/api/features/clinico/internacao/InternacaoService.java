package com.avicia.api.features.clinico.internacao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.clinico.internacao.request.InternacaoRequest;
import com.avicia.api.features.clinico.internacao.response.InternacaoResponse;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternacaoService {

    private final InternacaoRepository internacaoRepository;
    private final SystemLogService systemLogService;
    private final VerificarInternacao verificarInternacao;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarTodos() {
        return internacaoRepository.findAll().stream()
                .map(InternacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarPorPaciente(Integer idPaciente) {
        verificarInternacao.validarIdPacienteNaoNulo(idPaciente);
        return internacaoRepository.findByPaciente_IdPaciente(idPaciente).stream()
                .map(InternacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarPorPacienteOrdenado(Integer idPaciente) {
        verificarInternacao.validarIdPacienteNaoNulo(idPaciente);
        return internacaoRepository.findByPaciente_IdPacienteOrderByDataAdmissaoDesc(idPaciente).stream()
                .map(InternacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarPorProfissional(Integer idProfissional) {
        verificarInternacao.validarIdProfissionalSaudeNaoNulo(idProfissional);
        return internacaoRepository.findByProfissionalSaude_IdProfissional(idProfissional).stream()
                .map(InternacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarInternacoesAtivas() {
        return internacaoRepository.findByDataAltaIsNull().stream()
                .map(InternacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarInternacoesAtivasPorPaciente(Integer idPaciente) {
        verificarInternacao.validarIdPacienteNaoNulo(idPaciente);
        return internacaoRepository.findByPaciente_IdPacienteAndDataAltaIsNull(idPaciente).stream()
                .map(InternacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarInternacoesFinalizadas() {
        return internacaoRepository.findByDataAltaIsNotNull().stream()
                .map(InternacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return internacaoRepository.findByDataAdmissaoBetween(dataInicio, dataFim).stream()
                .map(InternacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> buscarPorLeito(String leito) {
        return internacaoRepository.findByLeitoContainingIgnoreCase(leito).stream()
                .map(InternacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> buscarLeitoAtivo(String leito) {
        return internacaoRepository.findByLeitoAndDataAltaIsNull(leito).stream()
                .map(InternacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InternacaoResponse buscarPorId(Integer id) {
        Internacao internacao = verificarInternacao.buscarInternacaoPorId(id);
        return InternacaoMapper.toResponseDTO(internacao);
    }

    @Transactional
    public InternacaoResponse criar(InternacaoRequest dto) {
        verificarInternacao.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarInternacao.validarIdProfissionalSaudeNaoNulo(dto.getIdProfissionalSaude());
        verificarInternacao.validarDataAdmissaoNaoNula(dto.getDataAdmissao());
        verificarInternacao.validarLeitoNaoVazio(dto.getLeito());
        
        Internacao internacao = InternacaoMapper.toEntity(dto);
        Paciente paciente = verificarInternacao.buscarPacientePorId(dto.getIdPaciente());
        ProfissionalSaude profissional = verificarInternacao.buscarProfissionalSaudePorId(dto.getIdProfissionalSaude());
        
        internacao.setPaciente(paciente);
        internacao.setProfissionalSaude(profissional);

        Internacao salva = internacaoRepository.save(internacao);
        
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Internacao",
            "Internação criada com ID " + salva.getIdInternacao() + " para paciente: " + paciente.getIdPaciente()
        );

        return InternacaoMapper.toResponseDTO(salva);
    }

    @Transactional
    public InternacaoResponse atualizar(Integer id, InternacaoRequest dto) {
        verificarInternacao.validarIdInternacaoNaoNulo(id);
        verificarInternacao.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarInternacao.validarIdProfissionalSaudeNaoNulo(dto.getIdProfissionalSaude());
        verificarInternacao.validarDataAdmissaoNaoNula(dto.getDataAdmissao());
        verificarInternacao.validarLeitoNaoVazio(dto.getLeito());
        
        Internacao existing = verificarInternacao.buscarInternacaoPorId(id);
        Paciente paciente = verificarInternacao.buscarPacientePorId(dto.getIdPaciente());
        ProfissionalSaude profissional = verificarInternacao.buscarProfissionalSaudePorId(dto.getIdProfissionalSaude());
        
        Internacao internacao = InternacaoMapper.toEntity(dto);
        internacao.setIdInternacao(existing.getIdInternacao());
        internacao.setPaciente(paciente);
        internacao.setProfissionalSaude(profissional);
        
        Internacao atualizada = internacaoRepository.save(internacao);
        
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Internacao",
            "Dados da internação com ID " + id + " foram atualizados"
        );
        
        return InternacaoMapper.toResponseDTO(atualizada);
    }

    @Transactional
    public InternacaoResponse registrarAlta(Integer id, LocalDate dataAlta) {
        verificarInternacao.validarIdInternacaoNaoNulo(id);
        if (dataAlta == null) {
            throw new IllegalArgumentException("Data de alta não pode ser nula");
        }
        
        Internacao internacao = verificarInternacao.buscarInternacaoPorId(id);
        internacao.setDataAlta(dataAlta);
        
        Internacao atualizada = internacaoRepository.save(internacao);
        
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Internacao",
            "Alta registrada para internação ID " + id
        );
        
        return InternacaoMapper.toResponseDTO(atualizada);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarInternacao.validarIdInternacaoNaoNulo(id);
        Internacao existing = verificarInternacao.buscarInternacaoPorId(id);
        internacaoRepository.delete(existing);
        
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Internacao",
            "Internação com ID " + id + " foi deletada"
        );
    }

}
