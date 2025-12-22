package com.avicia.api.features.paciente.dados.vacina;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.dados.vacina.request.PacienteVacinaRequest;
import com.avicia.api.features.paciente.dados.vacina.response.PacienteVacinaResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteVacinaService {

    private final PacienteVacinaRepository pacienteVacinaRepository;
    private final SystemLogService systemLogService;
    private final VerificarPacienteVacina verificarPacienteVacina;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<PacienteVacinaResponse> listarTodos() {
        return pacienteVacinaRepository.findAll()
                .stream()
                .map(PacienteVacinaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteVacinaResponse> listarPorPaciente(Integer idPaciente) {
        verificarPacienteVacina.validarIdPacienteNaoNulo(idPaciente);
        return pacienteVacinaRepository.findByPaciente_IdPaciente(idPaciente)
                .stream()
                .map(PacienteVacinaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteVacinaResponse> listarPorPacienteOrdenado(Integer idPaciente) {
        verificarPacienteVacina.validarIdPacienteNaoNulo(idPaciente);
        return pacienteVacinaRepository.findByPaciente_IdPacienteOrderByDataAplicacaoDesc(idPaciente)
                .stream()
                .map(PacienteVacinaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteVacinaResponse> buscarPorNomeVacina(Integer idPaciente, String vacina) {
        verificarPacienteVacina.validarIdPacienteNaoNulo(idPaciente);
        return pacienteVacinaRepository.findByPaciente_IdPacienteAndVacinaContainingIgnoreCase(
                idPaciente, vacina)
                .stream()
                .map(PacienteVacinaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteVacinaResponse> listarPorPeriodo(
            Integer idPaciente, 
            LocalDateTime dataInicio, 
            LocalDateTime dataFim) {
        verificarPacienteVacina.validarIdPacienteNaoNulo(idPaciente);
        return pacienteVacinaRepository.findByPaciente_IdPacienteAndDataAplicacaoBetween(
                idPaciente, dataInicio, dataFim)
                .stream()
                .map(PacienteVacinaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteVacinaResponse> listarVacinasRecentes(Integer idPaciente, LocalDateTime dataReferencia) {
        verificarPacienteVacina.validarIdPacienteNaoNulo(idPaciente);
        return pacienteVacinaRepository.findByPaciente_IdPacienteAndDataAplicacaoAfter(
                idPaciente, dataReferencia)
                .stream()
                .map(PacienteVacinaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PacienteVacinaResponse buscarPorId(Integer id) {
        PacienteVacina vacina = verificarPacienteVacina.buscarVacinaPorId(id);
        return PacienteVacinaMapper.toResponseDTO(vacina);
    }

    @Transactional
    public PacienteVacinaResponse criar(PacienteVacinaRequest dto) {
        // Validações
        verificarPacienteVacina.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteVacina.validarVacinaNaoVazia(dto.getVacina());
        verificarPacienteVacina.validarDataAplicacaoNaoNula(dto.getDataAplicacao());
        
        PacienteVacina vacina = PacienteVacinaMapper.toEntity(dto);

        // Recupera o paciente
        Paciente paciente = verificarPacienteVacina.buscarPacientePorId(dto.getIdPaciente());
        
        vacina.setPaciente(paciente);

        PacienteVacina salva = pacienteVacinaRepository.save(vacina);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteVacina",
            "Vacina criada com ID " + salva.getIdVacina() + " para paciente: " + paciente.getIdPaciente()
        );

        return PacienteVacinaMapper.toResponseDTO(salva);
    }

    @Transactional
    public PacienteVacinaResponse atualizar(Integer id, PacienteVacinaRequest dto) {
        verificarPacienteVacina.validarIdVacinaNaoNulo(id);
        verificarPacienteVacina.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteVacina.validarVacinaNaoVazia(dto.getVacina());
        verificarPacienteVacina.validarDataAplicacaoNaoNula(dto.getDataAplicacao());
        
        PacienteVacina existing = verificarPacienteVacina.buscarVacinaPorId(id);
        
        // Verifica se o paciente existe
        Paciente paciente = verificarPacienteVacina.buscarPacientePorId(dto.getIdPaciente());
        
        PacienteVacina vacina = PacienteVacinaMapper.toEntity(dto);
        vacina.setIdVacina(existing.getIdVacina());
        vacina.setPaciente(paciente);
        
        PacienteVacina atualizada = pacienteVacinaRepository.save(vacina);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteVacina",
            "Dados da vacina com ID " + id + " foram atualizados"
        );
        
        return PacienteVacinaMapper.toResponseDTO(atualizada);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarPacienteVacina.validarIdVacinaNaoNulo(id);
        
        PacienteVacina existing = verificarPacienteVacina.buscarVacinaPorId(id);
        
        pacienteVacinaRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteVacina",
            "Vacina com ID " + id + " foi deletada"
        );
    }

}
