package com.avicia.api.features.clinico.consulta.diagnostico;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.clinico.consulta.Consulta;
import com.avicia.api.features.clinico.consulta.diagnostico.request.ConsultaDiagnosticoRequest;
import com.avicia.api.features.clinico.consulta.diagnostico.response.ConsultaDiagnosticoResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaDiagnosticoService {

    private final ConsultaDiagnosticoRepository consultaDiagnosticoRepository;
    private final SystemLogService systemLogService;
    private final VerificarConsultaDiagnostico verificarConsultaDiagnostico;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<ConsultaDiagnosticoResponse> listarTodos() {
        return consultaDiagnosticoRepository.findAll()
                .stream()
                .map(ConsultaDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaDiagnosticoResponse> listarPorConsulta(Integer idConsulta) {
        verificarConsultaDiagnostico.validarIdConsultaNaoNulo(idConsulta);
        return consultaDiagnosticoRepository.findByConsulta_IdConsulta(idConsulta)
                .stream()
                .map(ConsultaDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaDiagnosticoResponse> listarPorCodigoCid(String codigoCidDez) {
        return consultaDiagnosticoRepository.findByCodigoCidDez(codigoCidDez)
                .stream()
                .map(ConsultaDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaDiagnosticoResponse> listarPorConsultaECodigoCid(
            Integer idConsulta, 
            String codigoCidDez) {
        verificarConsultaDiagnostico.validarIdConsultaNaoNulo(idConsulta);
        return consultaDiagnosticoRepository.findByConsulta_IdConsultaAndCodigoCidDez(idConsulta, codigoCidDez)
                .stream()
                .map(ConsultaDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaDiagnosticoResponse> buscarPorDescricao(String descricao) {
        return consultaDiagnosticoRepository.findByDescricaoContainingIgnoreCase(descricao)
                .stream()
                .map(ConsultaDiagnosticoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConsultaDiagnosticoResponse buscarPorId(Integer id) {
        ConsultaDiagnostico diagnostico = verificarConsultaDiagnostico.buscarDiagnosticoPorId(id);
        return ConsultaDiagnosticoMapper.toResponseDTO(diagnostico);
    }

    @Transactional
    public ConsultaDiagnosticoResponse criar(ConsultaDiagnosticoRequest dto) {
        // Validações
        verificarConsultaDiagnostico.validarIdConsultaNaoNulo(dto.getIdConsulta());
        verificarConsultaDiagnostico.validarCodigoCidDezNaoVazio(dto.getCodigoCidDez());
        
        ConsultaDiagnostico diagnostico = ConsultaDiagnosticoMapper.toEntity(dto);

        // Recupera a consulta
        Consulta consulta = verificarConsultaDiagnostico.buscarConsultaPorId(dto.getIdConsulta());
        
        diagnostico.setConsulta(consulta);

        ConsultaDiagnostico salvo = consultaDiagnosticoRepository.save(diagnostico);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ConsultaDiagnostico",
            "Diagnóstico criado com ID " + salvo.getIdDiagnostico() + " para consulta: " + consulta.getIdConsulta()
        );

        return ConsultaDiagnosticoMapper.toResponseDTO(salvo);
    }

    @Transactional
    public ConsultaDiagnosticoResponse atualizar(Integer id, ConsultaDiagnosticoRequest dto) {
        verificarConsultaDiagnostico.validarIdDiagnosticoNaoNulo(id);
        verificarConsultaDiagnostico.validarIdConsultaNaoNulo(dto.getIdConsulta());
        verificarConsultaDiagnostico.validarCodigoCidDezNaoVazio(dto.getCodigoCidDez());
        
        ConsultaDiagnostico existing = verificarConsultaDiagnostico.buscarDiagnosticoPorId(id);
        
        // Verifica se a consulta existe
        Consulta consulta = verificarConsultaDiagnostico.buscarConsultaPorId(dto.getIdConsulta());
        
        ConsultaDiagnostico diagnostico = ConsultaDiagnosticoMapper.toEntity(dto);
        diagnostico.setIdDiagnostico(existing.getIdDiagnostico());
        diagnostico.setConsulta(consulta);
        
        ConsultaDiagnostico atualizado = consultaDiagnosticoRepository.save(diagnostico);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ConsultaDiagnostico",
            "Dados do diagnóstico com ID " + id + " foram atualizados"
        );
        
        return ConsultaDiagnosticoMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarConsultaDiagnostico.validarIdDiagnosticoNaoNulo(id);
        
        ConsultaDiagnostico existing = verificarConsultaDiagnostico.buscarDiagnosticoPorId(id);
        
        consultaDiagnosticoRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "ConsultaDiagnostico",
            "Diagnóstico com ID " + id + " foi deletado"
        );
    }

}
