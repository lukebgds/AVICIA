package com.avicia.api.features.clinico.consulta.prescricao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.enumerate.StatusPrescricao;
import com.avicia.api.features.clinico.consulta.Consulta;
import com.avicia.api.features.clinico.consulta.prescricao.request.ConsultaPrescricaoRequest;
import com.avicia.api.features.clinico.consulta.prescricao.response.ConsultaPrescricaoResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaPrescricaoService {

    private final ConsultaPrescricaoRepository consultaPrescricaoRepository;
    private final SystemLogService systemLogService;
    private final VerificarConsultaPrescricao verificarConsultaPrescricao;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<ConsultaPrescricaoResponse> listarTodos() {
        return consultaPrescricaoRepository.findAll()
                .stream()
                .map(ConsultaPrescricaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaPrescricaoResponse> listarPorConsulta(Integer idConsulta) {
        verificarConsultaPrescricao.validarIdConsultaNaoNulo(idConsulta);
        return consultaPrescricaoRepository.findByConsulta_IdConsulta(idConsulta)
                .stream()
                .map(ConsultaPrescricaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaPrescricaoResponse> listarPorConsultaOrdenado(Integer idConsulta) {
        verificarConsultaPrescricao.validarIdConsultaNaoNulo(idConsulta);
        return consultaPrescricaoRepository.findByConsulta_IdConsultaOrderByDataEmissaoDesc(idConsulta)
                .stream()
                .map(ConsultaPrescricaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaPrescricaoResponse> listarPorStatus(StatusPrescricao status) {
        return consultaPrescricaoRepository.findByStatus(status)
                .stream()
                .map(ConsultaPrescricaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaPrescricaoResponse> listarPorConsultaEStatus(
            Integer idConsulta, 
            StatusPrescricao status) {
        verificarConsultaPrescricao.validarIdConsultaNaoNulo(idConsulta);
        return consultaPrescricaoRepository.findByConsulta_IdConsultaAndStatus(idConsulta, status)
                .stream()
                .map(ConsultaPrescricaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaPrescricaoResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return consultaPrescricaoRepository.findByDataEmissaoBetween(dataInicio, dataFim)
                .stream()
                .map(ConsultaPrescricaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaPrescricaoResponse> listarPorConsultaEPeriodo(
            Integer idConsulta, 
            LocalDate dataInicio, 
            LocalDate dataFim) {
        verificarConsultaPrescricao.validarIdConsultaNaoNulo(idConsulta);
        return consultaPrescricaoRepository.findByConsulta_IdConsultaAndDataEmissaoBetween(
                idConsulta, dataInicio, dataFim)
                .stream()
                .map(ConsultaPrescricaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaPrescricaoResponse> listarPrescricoesRecentes(LocalDate dataReferencia) {
        return consultaPrescricaoRepository.findByDataEmissaoAfter(dataReferencia)
                .stream()
                .map(ConsultaPrescricaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConsultaPrescricaoResponse buscarPorId(Integer id) {
        ConsultaPrescricao prescricao = verificarConsultaPrescricao.buscarPrescricaoPorId(id);
        return ConsultaPrescricaoMapper.toResponseDTO(prescricao);
    }

    @Transactional
    public ConsultaPrescricaoResponse criar(ConsultaPrescricaoRequest dto) {
        // Validações
        verificarConsultaPrescricao.validarIdConsultaNaoNulo(dto.getIdConsulta());
        verificarConsultaPrescricao.validarDataEmissaoNaoNula(dto.getDataEmissao());
        verificarConsultaPrescricao.validarStatusNaoNulo(dto.getStatus());
        
        ConsultaPrescricao prescricao = ConsultaPrescricaoMapper.toEntity(dto);

        // Recupera a consulta
        Consulta consulta = verificarConsultaPrescricao.buscarConsultaPorId(dto.getIdConsulta());
        
        prescricao.setConsulta(consulta);

        ConsultaPrescricao salva = consultaPrescricaoRepository.save(prescricao);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ConsultaPrescricao",
            "Prescrição criada com ID " + salva.getIdPrescricao() + " para consulta: " + consulta.getIdConsulta()
        );

        return ConsultaPrescricaoMapper.toResponseDTO(salva);
    }

    @Transactional
    public ConsultaPrescricaoResponse atualizar(Integer id, ConsultaPrescricaoRequest dto) {
        verificarConsultaPrescricao.validarIdPrescricaoNaoNulo(id);
        verificarConsultaPrescricao.validarIdConsultaNaoNulo(dto.getIdConsulta());
        verificarConsultaPrescricao.validarDataEmissaoNaoNula(dto.getDataEmissao());
        verificarConsultaPrescricao.validarStatusNaoNulo(dto.getStatus());
        
        ConsultaPrescricao existing = verificarConsultaPrescricao.buscarPrescricaoPorId(id);
        
        // Verifica se a consulta existe
        Consulta consulta = verificarConsultaPrescricao.buscarConsultaPorId(dto.getIdConsulta());
        
        ConsultaPrescricao prescricao = ConsultaPrescricaoMapper.toEntity(dto);
        prescricao.setIdPrescricao(existing.getIdPrescricao());
        prescricao.setConsulta(consulta);
        
        ConsultaPrescricao atualizada = consultaPrescricaoRepository.save(prescricao);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ConsultaPrescricao",
            "Dados da prescrição com ID " + id + " foram atualizados"
        );
        
        return ConsultaPrescricaoMapper.toResponseDTO(atualizada);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarConsultaPrescricao.validarIdPrescricaoNaoNulo(id);
        
        ConsultaPrescricao existing = verificarConsultaPrescricao.buscarPrescricaoPorId(id);
        
        consultaPrescricaoRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ConsultaPrescricao",
            "Prescrição com ID " + id + " foi deletada"
        );
    }

}
