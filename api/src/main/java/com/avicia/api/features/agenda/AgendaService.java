package com.avicia.api.features.agenda;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.enumerate.StatusAgenda;
import com.avicia.api.features.agenda.request.AgendaRequest;
import com.avicia.api.features.agenda.response.AgendaResponse;
import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.profissional.ProfissionalSaude;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final SystemLogService systemLogService;
    private final VerificarAgenda verificarAgenda;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarTodos() {
        return agendaRepository.findAll()
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarPorProfissionalSaude(Integer idProfissionalSaude) {
        verificarAgenda.validarIdProfissionalSaudeNaoNulo(idProfissionalSaude);
        return agendaRepository.findByProfissionalSaude_IdProfissional(idProfissionalSaude)
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarPorProfissionalSaudeOrdenado(Integer idProfissionalSaude) {
        verificarAgenda.validarIdProfissionalSaudeNaoNulo(idProfissionalSaude);
        return agendaRepository.findByProfissionalSaude_IdProfissionalOrderByDataHorarioAsc(idProfissionalSaude)
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarPorPaciente(Integer idPaciente) {
        verificarAgenda.validarIdPacienteNaoNulo(idPaciente);
        return agendaRepository.findByPaciente_IdPaciente(idPaciente)
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarPorPacienteOrdenado(Integer idPaciente) {
        verificarAgenda.validarIdPacienteNaoNulo(idPaciente);
        return agendaRepository.findByPaciente_IdPacienteOrderByDataHorarioAsc(idPaciente)
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarPorStatus(StatusAgenda status) {
        return agendaRepository.findByStatus(status)
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarPorProfissionalEStatus(Integer idProfissionalSaude, StatusAgenda status) {
        verificarAgenda.validarIdProfissionalSaudeNaoNulo(idProfissionalSaude);
        return agendaRepository.findByProfissionalSaude_IdProfissionalAndStatus(idProfissionalSaude, status)
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarPorPacienteEStatus(Integer idPaciente, StatusAgenda status) {
        verificarAgenda.validarIdPacienteNaoNulo(idPaciente);
        return agendaRepository.findByPaciente_IdPacienteAndStatus(idPaciente, status)
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return agendaRepository.findByDataHorarioBetween(dataInicio, dataFim)
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarPorProfissionalEPeriodo(
            Integer idProfissionalSaude, 
            LocalDateTime dataInicio, 
            LocalDateTime dataFim) {
        verificarAgenda.validarIdProfissionalSaudeNaoNulo(idProfissionalSaude);
        return agendaRepository.findByProfissionalSaude_IdProfissionalAndDataHorarioBetween(
                idProfissionalSaude, dataInicio, dataFim)
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarPorPacienteEPeriodo(
            Integer idPaciente, 
            LocalDateTime dataInicio, 
            LocalDateTime dataFim) {
        verificarAgenda.validarIdPacienteNaoNulo(idPaciente);
        return agendaRepository.findByPaciente_IdPacienteAndDataHorarioBetween(
                idPaciente, dataInicio, dataFim)
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarAgendasFuturasProfissional(Integer idProfissionalSaude) {
        verificarAgenda.validarIdProfissionalSaudeNaoNulo(idProfissionalSaude);
        return agendaRepository.findByProfissionalSaude_IdProfissionalAndDataHorarioAfter(
                idProfissionalSaude, LocalDateTime.now())
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> listarAgendasFuturasPaciente(Integer idPaciente) {
        verificarAgenda.validarIdPacienteNaoNulo(idPaciente);
        return agendaRepository.findByPaciente_IdPacienteAndDataHorarioAfter(
                idPaciente, LocalDateTime.now())
                .stream()
                .map(AgendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AgendaResponse buscarPorId(Integer id) {
        Agenda agenda = verificarAgenda.buscarAgendaPorId(id);
        return AgendaMapper.toResponseDTO(agenda);
    }

    @Transactional
    public AgendaResponse criar(AgendaRequest dto) {
        // Validações
        verificarAgenda.validarIdProfissionalSaudeNaoNulo(dto.getIdProfissionalSaude());
        verificarAgenda.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarAgenda.validarDataHorarioNaoNulo(dto.getDataHorario());
        verificarAgenda.validarStatusNaoNulo(dto.getStatus());
        
        Agenda agenda = AgendaMapper.toEntity(dto);

        // Recupera o profissional de saúde
        ProfissionalSaude profissional = verificarAgenda.buscarProfissionalSaudePorId(dto.getIdProfissionalSaude());
        
        // Recupera o paciente
        Paciente paciente = verificarAgenda.buscarPacientePorId(dto.getIdPaciente());
        
        agenda.setProfissionalSaude(profissional);
        agenda.setPaciente(paciente);

        Agenda salva = agendaRepository.save(agenda);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Agenda",
            "Agenda criada com ID " + salva.getIdAgenda() + " para profissional: " + 
            profissional.getIdProfissional() + " e paciente: " + paciente.getIdPaciente()
        );

        return AgendaMapper.toResponseDTO(salva);
    }

    @Transactional
    public AgendaResponse atualizar(Integer id, AgendaRequest dto) {
        verificarAgenda.validarIdAgendaNaoNulo(id);
        verificarAgenda.validarIdProfissionalSaudeNaoNulo(dto.getIdProfissionalSaude());
        verificarAgenda.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarAgenda.validarDataHorarioNaoNulo(dto.getDataHorario());
        verificarAgenda.validarStatusNaoNulo(dto.getStatus());
        
        Agenda existing = verificarAgenda.buscarAgendaPorId(id);
        
        // Verifica se o profissional de saúde existe
        ProfissionalSaude profissional = verificarAgenda.buscarProfissionalSaudePorId(dto.getIdProfissionalSaude());
        
        // Verifica se o paciente existe
        Paciente paciente = verificarAgenda.buscarPacientePorId(dto.getIdPaciente());
        
        Agenda agenda = AgendaMapper.toEntity(dto);
        agenda.setIdAgenda(existing.getIdAgenda());
        agenda.setProfissionalSaude(profissional);
        agenda.setPaciente(paciente);
        
        Agenda atualizada = agendaRepository.save(agenda);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Agenda",
            "Dados da agenda com ID " + id + " foram atualizados"
        );
        
        return AgendaMapper.toResponseDTO(atualizada);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarAgenda.validarIdAgendaNaoNulo(id);
        
        Agenda existing = verificarAgenda.buscarAgendaPorId(id);
        
        agendaRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "Agenda",
            "Agenda com ID " + id + " foi deletada"
        );
    }

}
