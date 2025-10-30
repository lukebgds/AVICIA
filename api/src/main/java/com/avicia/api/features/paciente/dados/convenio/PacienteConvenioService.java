package com.avicia.api.features.paciente.dados.convenio;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.dados.convenio.request.PacienteConvenioRequest;
import com.avicia.api.features.paciente.dados.convenio.response.PacienteConvenioResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteConvenioService {

    private final PacienteConvenioRepository pacienteConvenioRepository;
    private final SystemLogService systemLogService;
    private final VerificarPacienteConvenio verificarPacienteConvenio;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<PacienteConvenioResponse> listarTodos() {
        return pacienteConvenioRepository.findAll()
                .stream()
                .map(PacienteConvenioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteConvenioResponse> listarPorPaciente(Integer idPaciente) {
        verificarPacienteConvenio.validarIdPacienteNaoNulo(idPaciente);
        return pacienteConvenioRepository.findByPaciente_IdPaciente(idPaciente)
                .stream()
                .map(PacienteConvenioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteConvenioResponse> listarPorPacienteENomeConvenio(Integer idPaciente, String nomeConvenio) {
        verificarPacienteConvenio.validarIdPacienteNaoNulo(idPaciente);
        return pacienteConvenioRepository.findByPaciente_IdPacienteAndNomeConvenio(idPaciente, nomeConvenio)
                .stream()
                .map(PacienteConvenioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteConvenioResponse> listarConveniosValidos(Integer idPaciente) {
        verificarPacienteConvenio.validarIdPacienteNaoNulo(idPaciente);
        return pacienteConvenioRepository.findByPaciente_IdPacienteAndValidadeGreaterThanEqual(idPaciente, LocalDate.now())
                .stream()
                .map(PacienteConvenioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteConvenioResponse> listarConveniosVencidos(Integer idPaciente) {
        verificarPacienteConvenio.validarIdPacienteNaoNulo(idPaciente);
        return pacienteConvenioRepository.findByPaciente_IdPacienteAndValidadeLessThan(idPaciente, LocalDate.now())
                .stream()
                .map(PacienteConvenioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PacienteConvenioResponse buscarPorId(Integer id) {
        PacienteConvenio convenio = verificarPacienteConvenio.buscarConvenioPorId(id);
        return PacienteConvenioMapper.toResponseDTO(convenio);
    }

    @Transactional
    public PacienteConvenioResponse criar(PacienteConvenioRequest dto) {
        // Validações
        verificarPacienteConvenio.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteConvenio.validarNomeConvenioNaoVazio(dto.getNomeConvenio());
        verificarPacienteConvenio.validarNumeroCarteirinhaNaoVazio(dto.getNumeroCarteirinha());
        
        // Verifica se a carteirinha já existe
        verificarPacienteConvenio.verificarCarteirinhaDuplicada(dto.getNumeroCarteirinha());
        
        PacienteConvenio convenio = PacienteConvenioMapper.toEntity(dto);

        // Recupera o paciente
        Paciente paciente = verificarPacienteConvenio.buscarPacientePorId(dto.getIdPaciente());
        
        convenio.setPaciente(paciente);

        PacienteConvenio salvo = pacienteConvenioRepository.save(convenio);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteConvenio",
            "Convênio criado com ID " + salvo.getIdConvenio() + " para paciente: " + paciente.getIdPaciente()
        );

        return PacienteConvenioMapper.toResponseDTO(salvo);
    }

    @Transactional
    public PacienteConvenioResponse atualizar(Integer id, PacienteConvenioRequest dto) {
        verificarPacienteConvenio.validarIdConvenioNaoNulo(id);
        verificarPacienteConvenio.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteConvenio.validarNomeConvenioNaoVazio(dto.getNomeConvenio());
        verificarPacienteConvenio.validarNumeroCarteirinhaNaoVazio(dto.getNumeroCarteirinha());
        
        PacienteConvenio existing = verificarPacienteConvenio.buscarConvenioPorId(id);
        
        // Verifica se a carteirinha já existe para outro convênio
        verificarPacienteConvenio.verificarCarteirinhaDuplicadaAtualizacao(dto.getNumeroCarteirinha(), id);
        
        // Verifica se o paciente existe
        Paciente paciente = verificarPacienteConvenio.buscarPacientePorId(dto.getIdPaciente());
        
        PacienteConvenio convenio = PacienteConvenioMapper.toEntity(dto);
        convenio.setIdConvenio(existing.getIdConvenio());
        convenio.setPaciente(paciente);
        
        PacienteConvenio atualizado = pacienteConvenioRepository.save(convenio);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteConvenio",
            "Dados do convênio com ID " + id + " foram atualizados"
        );
        
        return PacienteConvenioMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarPacienteConvenio.validarIdConvenioNaoNulo(id);
        
        PacienteConvenio existing = verificarPacienteConvenio.buscarConvenioPorId(id);
        
        pacienteConvenioRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteConvenio",
            "Convênio com ID " + id + " foi deletado"
        );
    }

}
