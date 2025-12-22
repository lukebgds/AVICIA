package com.avicia.api.features.paciente.dados.anexo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.paciente.Paciente;
import com.avicia.api.features.paciente.dados.anexo.request.PacienteAnexoRequest;
import com.avicia.api.features.paciente.dados.anexo.response.PacienteAnexoResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteAnexoService {

    private final PacienteAnexoRepository pacienteAnexoRepository;
    private final SystemLogService systemLogService;
    private final VerificarPacienteAnexo verificarPacienteAnexo;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<PacienteAnexoResponse> listarTodos() {
        return pacienteAnexoRepository.findAll()
                .stream()
                .map(PacienteAnexoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteAnexoResponse> listarPorPaciente(Integer idPaciente) {
        verificarPacienteAnexo.validarIdPacienteNaoNulo(idPaciente);
        return pacienteAnexoRepository.findByPaciente_IdPaciente(idPaciente)
                .stream()
                .map(PacienteAnexoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PacienteAnexoResponse> listarPorPacienteETipo(Integer idPaciente, String tipo) {
        verificarPacienteAnexo.validarIdPacienteNaoNulo(idPaciente);
        return pacienteAnexoRepository.findByPaciente_IdPacienteAndTipo(idPaciente, tipo)
                .stream()
                .map(PacienteAnexoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PacienteAnexoResponse buscarPorId(Integer id) {
        PacienteAnexo anexo = verificarPacienteAnexo.buscarAnexoPorId(id);
        return PacienteAnexoMapper.toResponseDTO(anexo);
    }

    @Transactional
    public PacienteAnexoResponse criar(PacienteAnexoRequest dto) {
        // Validações
        verificarPacienteAnexo.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteAnexo.validarUrlArquivoNaoVazia(dto.getUrlArquivo());
        
        PacienteAnexo anexo = PacienteAnexoMapper.toEntity(dto);

        // Recupera o paciente
        Paciente paciente = verificarPacienteAnexo.buscarPacientePorId(dto.getIdPaciente());
        
        anexo.setPaciente(paciente);
        anexo.setDataUpload(LocalDateTime.now());

        PacienteAnexo salvo = pacienteAnexoRepository.save(anexo);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteAnexo",
            "Anexo criado com ID " + salvo.getIdAnexo() + " para paciente: " + paciente.getIdPaciente()
        );

        return PacienteAnexoMapper.toResponseDTO(salvo);
    }

    @Transactional
    public PacienteAnexoResponse atualizar(Integer id, PacienteAnexoRequest dto) {
        verificarPacienteAnexo.validarIdAnexoNaoNulo(id);
        verificarPacienteAnexo.validarIdPacienteNaoNulo(dto.getIdPaciente());
        verificarPacienteAnexo.validarUrlArquivoNaoVazia(dto.getUrlArquivo());
        
        PacienteAnexo existing = verificarPacienteAnexo.buscarAnexoPorId(id);
        
        // Verifica se o paciente existe
        Paciente paciente = verificarPacienteAnexo.buscarPacientePorId(dto.getIdPaciente());
        
        PacienteAnexo anexo = PacienteAnexoMapper.toEntity(dto);
        anexo.setIdAnexo(existing.getIdAnexo());
        anexo.setPaciente(paciente);
        anexo.setDataUpload(existing.getDataUpload()); // Mantém a data original de upload
        
        PacienteAnexo atualizado = pacienteAnexoRepository.save(anexo);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteAnexo",
            "Dados do anexo com ID " + id + " foram atualizados"
        );
        
        return PacienteAnexoMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarPacienteAnexo.validarIdAnexoNaoNulo(id);
        
        PacienteAnexo existing = verificarPacienteAnexo.buscarAnexoPorId(id);
        
        pacienteAnexoRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PacienteAnexo",
            "Anexo com ID " + id + " foi deletado"
        );
    }

}
