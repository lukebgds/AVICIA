package com.avicia.api.features.paciente.dados.antecedente;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.dados.antecedente.request.PacienteAntecedenteRequest;
import com.avicia.api.features.paciente.dados.antecedente.response.PacienteAntecedenteResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteAntecedenteService {

    private final PacienteAntecedenteRepository pacienteAntecedenteRepository;
    private final SystemLogService systemLogService;
    private final VerificarPacienteAntecedente verificarPacienteAntecedente;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<PacienteAntecedenteResponse> listarTodos() {
        return pacienteAntecedenteRepository.findAll()
                .stream()
                .map(PacienteAntecedenteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteAntecedenteResponse> listarPorPaciente(Integer idPaciente) {
        verificarPacienteAntecedente.validarIdPacienteNaoNulo(idPaciente);
        return pacienteAntecedenteRepository.findByPaciente_IdPaciente(idPaciente)
                .stream()
                .map(PacienteAntecedenteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteAntecedenteResponse> listarPorPacienteETipoDoenca(Integer idPaciente, String tipoDoenca) {
        verificarPacienteAntecedente.validarIdPacienteNaoNulo(idPaciente);
        return pacienteAntecedenteRepository.findByPaciente_IdPacienteAndTipoDoenca(idPaciente, tipoDoenca)
                .stream()
                .map(PacienteAntecedenteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteAntecedenteResponse> listarPorPacienteEParentesco(Integer idPaciente, String parentesco) {
        verificarPacienteAntecedente.validarIdPacienteNaoNulo(idPaciente);
        return pacienteAntecedenteRepository.findByPaciente_IdPacienteAndParentesco(idPaciente, parentesco)
                .stream()
                .map(PacienteAntecedenteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PacienteAntecedenteResponse buscarPorId(Integer id) {
        PacienteAntecedente antecedente = verificarPacienteAntecedente.buscarAntecedentePorId(id);
        return PacienteAntecedenteMapper.toResponseDTO(antecedente);
    }

    @Transactional
    public PacienteAntecedenteResponse criar(PacienteAntecedenteRequest dto) {
        // Validações
        verificarPacienteAntecedente.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteAntecedente.validarTipoDoencaNaoVazio(dto.getTipoDoenca());
        
        PacienteAntecedente antecedente = PacienteAntecedenteMapper.toEntity(dto);

        // Recupera o paciente
        Paciente paciente = verificarPacienteAntecedente.buscarPacientePorId(dto.getIdPaciente());
        
        antecedente.setPaciente(paciente);

        PacienteAntecedente salvo = pacienteAntecedenteRepository.save(antecedente);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteAntecedente",
            "Antecedente criado com ID " + salvo.getIdAntecedente() + " para paciente: " + paciente.getIdPaciente()
        );

        return PacienteAntecedenteMapper.toResponseDTO(salvo);
    }

    @Transactional
    public PacienteAntecedenteResponse atualizar(Integer id, PacienteAntecedenteRequest dto) {
        verificarPacienteAntecedente.validarIdAntecedenteNaoNulo(id);
        verificarPacienteAntecedente.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteAntecedente.validarTipoDoencaNaoVazio(dto.getTipoDoenca());
        
        PacienteAntecedente existing = verificarPacienteAntecedente.buscarAntecedentePorId(id);
        
        // Verifica se o paciente existe
        Paciente paciente = verificarPacienteAntecedente.buscarPacientePorId(dto.getIdPaciente());
        
        PacienteAntecedente antecedente = PacienteAntecedenteMapper.toEntity(dto);
        antecedente.setIdAntecedente(existing.getIdAntecedente());
        antecedente.setPaciente(paciente);
        
        PacienteAntecedente atualizado = pacienteAntecedenteRepository.save(antecedente);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteAntecedente",
            "Dados do antecedente com ID " + id + " foram atualizados"
        );
        
        return PacienteAntecedenteMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarPacienteAntecedente.validarIdAntecedenteNaoNulo(id);
        
        PacienteAntecedente existing = verificarPacienteAntecedente.buscarAntecedentePorId(id);
        
        pacienteAntecedenteRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteAntecedente",
            "Antecedente com ID " + id + " foi deletado"
        );
    }

}
