package com.avicia.api.features.paciente.dados.alergia;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.dados.alergia.request.PacienteAlergiaRequest;
import com.avicia.api.features.paciente.dados.alergia.response.PacienteAlergiaResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteAlergiaService {

    private final PacienteAlergiaRepository pacienteAlergiaRepository;
    private final SystemLogService systemLogService;
    private final VerificarPacienteAlergia verificarPacienteAlergia;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<PacienteAlergiaResponse> listarTodos() {
        return pacienteAlergiaRepository.findAll()
                .stream()
                .map(PacienteAlergiaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteAlergiaResponse> listarPorPaciente(Integer idPaciente) {
        verificarPacienteAlergia.validarIdPacienteNaoNulo(idPaciente);
        return pacienteAlergiaRepository.findByPaciente_IdPaciente(idPaciente)
                .stream()
                .map(PacienteAlergiaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PacienteAlergiaResponse buscarPorId(Integer id) {
        PacienteAlergia alergia = verificarPacienteAlergia.buscarAlergiaPorId(id);
        return PacienteAlergiaMapper.toResponseDTO(alergia);
    }

    @Transactional
    public PacienteAlergiaResponse criar(PacienteAlergiaRequest dto) {
        // Validações
        verificarPacienteAlergia.validarIdPacienteNaoNulo(dto.getIdPaciente());
        
        PacienteAlergia alergia = PacienteAlergiaMapper.toEntity(dto);

        // Recupera o paciente
        Paciente paciente = verificarPacienteAlergia.buscarPacientePorId(dto.getIdPaciente());
        
        alergia.setPaciente(paciente);

        PacienteAlergia salva = pacienteAlergiaRepository.save(alergia);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteAlergia",
            "Alergia criada com ID " + salva.getIdAlergia() + " para paciente: " + paciente.getIdPaciente()
        );

        return PacienteAlergiaMapper.toResponseDTO(salva);
    }

    @Transactional
    public PacienteAlergiaResponse atualizar(Integer id, PacienteAlergiaRequest dto) {
        verificarPacienteAlergia.validarIdAlergiaNaoNulo(id);
        verificarPacienteAlergia.validarIdPacienteNaoNulo(dto.getIdPaciente());
        
        PacienteAlergia existing = verificarPacienteAlergia.buscarAlergiaPorId(id);
        
        // Verifica se o paciente existe
        Paciente paciente = verificarPacienteAlergia.buscarPacientePorId(dto.getIdPaciente());
        
        PacienteAlergia alergia = PacienteAlergiaMapper.toEntity(dto);
        alergia.setIdAlergia(existing.getIdAlergia());
        alergia.setPaciente(paciente);
        
        PacienteAlergia atualizada = pacienteAlergiaRepository.save(alergia);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteAlergia",
            "Dados da alergia com ID " + id + " foram atualizados"
        );
        
        return PacienteAlergiaMapper.toResponseDTO(atualizada);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarPacienteAlergia.validarIdAlergiaNaoNulo(id);
        
        PacienteAlergia existing = verificarPacienteAlergia.buscarAlergiaPorId(id);
        
        pacienteAlergiaRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteAlergia",
            "Alergia com ID " + id + " foi deletada"
        );
    }

}
