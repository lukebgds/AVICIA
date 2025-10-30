package com.avicia.api.features.paciente.dados.medicamento;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.dados.medicamento.request.PacienteMedicamentoRequest;
import com.avicia.api.features.paciente.dados.medicamento.response.PacienteMedicamentoResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteMedicamentoService {

    private final PacienteMedicamentoRepository pacienteMedicamentoRepository;
    private final SystemLogService systemLogService;
    private final VerificarPacienteMedicamento verificarPacienteMedicamento;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<PacienteMedicamentoResponse> listarTodos() {
        return pacienteMedicamentoRepository.findAll()
                .stream()
                .map(PacienteMedicamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteMedicamentoResponse> listarPorPaciente(Integer idPaciente) {
        verificarPacienteMedicamento.validarIdPacienteNaoNulo(idPaciente);
        return pacienteMedicamentoRepository.findByPaciente_IdPaciente(idPaciente)
                .stream()
                .map(PacienteMedicamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteMedicamentoResponse> buscarPorNomeMedicamento(Integer idPaciente, String medicamento) {
        verificarPacienteMedicamento.validarIdPacienteNaoNulo(idPaciente);
        return pacienteMedicamentoRepository.findByPaciente_IdPacienteAndMedicamentoContainingIgnoreCase(
                idPaciente, medicamento)
                .stream()
                .map(PacienteMedicamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteMedicamentoResponse> listarPorFrequencia(Integer idPaciente, String frequencia) {
        verificarPacienteMedicamento.validarIdPacienteNaoNulo(idPaciente);
        return pacienteMedicamentoRepository.findByPaciente_IdPacienteAndFrequencia(idPaciente, frequencia)
                .stream()
                .map(PacienteMedicamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PacienteMedicamentoResponse buscarPorId(Integer id) {
        PacienteMedicamento medicamento = verificarPacienteMedicamento.buscarMedicamentoPorId(id);
        return PacienteMedicamentoMapper.toResponseDTO(medicamento);
    }

    @Transactional
    public PacienteMedicamentoResponse criar(PacienteMedicamentoRequest dto) {
        // Validações
        verificarPacienteMedicamento.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteMedicamento.validarMedicamentoNaoVazio(dto.getMedicamento());
        verificarPacienteMedicamento.validarDosagemNaoVazia(dto.getDosagem());
        
        PacienteMedicamento medicamento = PacienteMedicamentoMapper.toEntity(dto);

        // Recupera o paciente
        Paciente paciente = verificarPacienteMedicamento.buscarPacientePorId(dto.getIdPaciente());
        
        medicamento.setPaciente(paciente);

        PacienteMedicamento salvo = pacienteMedicamentoRepository.save(medicamento);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteMedicamento",
            "Medicamento criado com ID " + salvo.getIdMedicamento() + " para paciente: " + paciente.getIdPaciente()
        );

        return PacienteMedicamentoMapper.toResponseDTO(salvo);
    }

    @Transactional
    public PacienteMedicamentoResponse atualizar(Integer id, PacienteMedicamentoRequest dto) {
        verificarPacienteMedicamento.validarIdMedicamentoNaoNulo(id);
        verificarPacienteMedicamento.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteMedicamento.validarMedicamentoNaoVazio(dto.getMedicamento());
        verificarPacienteMedicamento.validarDosagemNaoVazia(dto.getDosagem());
        
        PacienteMedicamento existing = verificarPacienteMedicamento.buscarMedicamentoPorId(id);
        
        // Verifica se o paciente existe
        Paciente paciente = verificarPacienteMedicamento.buscarPacientePorId(dto.getIdPaciente());
        
        PacienteMedicamento medicamento = PacienteMedicamentoMapper.toEntity(dto);
        medicamento.setIdMedicamento(existing.getIdMedicamento());
        medicamento.setPaciente(paciente);
        
        PacienteMedicamento atualizado = pacienteMedicamentoRepository.save(medicamento);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteMedicamento",
            "Dados do medicamento com ID " + id + " foram atualizados"
        );
        
        return PacienteMedicamentoMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarPacienteMedicamento.validarIdMedicamentoNaoNulo(id);
        
        PacienteMedicamento existing = verificarPacienteMedicamento.buscarMedicamentoPorId(id);
        
        pacienteMedicamentoRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteMedicamento",
            "Medicamento com ID " + id + " foi deletado"
        );
    }

}
