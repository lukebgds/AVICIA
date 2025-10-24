package com.avicia.api.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.dto.request.paciente.PacienteRequest;
import com.avicia.api.data.dto.response.paciente.PacienteResponse;
import com.avicia.api.data.mapper.PacienteMapper;
import com.avicia.api.exception.BusinessException;
import com.avicia.api.model.Paciente;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.PacienteRepository;
import com.avicia.api.security.verify.VerificarPaciente;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final SystemLogService systemLogService;
    private final VerificarPaciente verificarPaciente;

    @Transactional(readOnly = true)
    public List<PacienteResponse> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(PacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PacienteResponse buscarPorId(Integer id) {
        Paciente paciente = verificarPaciente.buscarPacientePorId(id);
        return PacienteMapper.toResponseDTO(paciente);
    }

    @Transactional(readOnly = true)
    public PacienteResponse buscarPorCpf(String cpf) {
        Paciente paciente = verificarPaciente.buscarPacientePorCpf(cpf);
        return PacienteMapper.toResponseDTO(paciente);
    }

    @Transactional
    public PacienteResponse criar(PacienteRequest dto) {
        // Validações
        verificarPaciente.validarIdUsuarioNaoNulo(dto.getIdUsuario());
        
        Paciente paciente = PacienteMapper.toEntity(dto);

        // Recupera o usuário
        Usuario usuario = verificarPaciente.buscarUsuarioPorId(dto.getIdUsuario());
        
        // Verifica se já existe um paciente para este usuário
        verificarPaciente.verificarPacienteDuplicado(usuario.getCpf());
        
        paciente.setUsuario(usuario);

        // Geração do ID do paciente
        Integer idPaciente = gerarIdUnicoPaciente(usuario.getIdUsuario());
        paciente.setIdPaciente(idPaciente);

        Paciente salvo = pacienteRepository.save(paciente);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            salvo.getIdPaciente(),
            "Paciente",
            "Paciente criado com ID " + salvo.getIdPaciente() + " para usuário: " + usuario.getCpf()
        );

        return PacienteMapper.toResponseDTO(salvo);
    }

    @Transactional
    public PacienteResponse atualizar(String cpf, PacienteRequest dto) {
        verificarPaciente.validarCpfNaoVazio(cpf);
        verificarPaciente.validarIdUsuarioNaoNulo(dto.getIdUsuario());
        
        Paciente existing = verificarPaciente.buscarPacientePorCpf(cpf);
        
        // Verifica se o usuário existe
        Usuario usuario = verificarPaciente.buscarUsuarioPorIdAtualizacao(dto.getIdUsuario(), existing.getIdPaciente());
        
        Paciente paciente = PacienteMapper.toEntity(dto);
        paciente.setIdPaciente(existing.getIdPaciente());
        paciente.setUsuario(usuario);
        
        Paciente atualizado = pacienteRepository.save(paciente);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            atualizado.getIdPaciente(),
            "Paciente",
            "Dados do paciente com CPF " + cpf + " foram atualizados"
        );
        
        return PacienteMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(String cpf) {
        verificarPaciente.validarCpfNaoVazio(cpf);
        
        Paciente existing = verificarPaciente.buscarPacientePorCpf(cpf);
        Integer idPaciente = existing.getIdPaciente();
        
        pacienteRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            idPaciente,
            "Paciente",
            "Paciente com CPF " + cpf + " foi deletado"
        );
    }
    
    // ================= MÉTODOS AUXILIARES ================= //
    
    private Integer gerarIdUnicoPaciente(Integer idUsuario) {
        int tentativas = 0;
        int maxTentativas = 1000;
        
        verificarPaciente.validarIdUsuarioParaGeracao(idUsuario);
        
        String idUsuarioStr = String.valueOf(idUsuario);
        String prefixo = idUsuarioStr.substring(0, 3);
        
        while (tentativas < maxTentativas) {
            String numeroAleatorio = String.format("%03d", new Random().nextInt(1_000));
            Integer idPaciente = Integer.parseInt(prefixo + numeroAleatorio);
            
            if (!verificarPaciente.idPacienteExiste(idPaciente)) {
                return idPaciente;
            }
            tentativas++;
        }
        
        // Se chegou aqui, não conseguiu gerar ID único após todas as tentativas
        throw new BusinessException(
            100,
            "Paciente",
            "Não foi possível gerar um ID único para o paciente após %d tentativas. Tente novamente.",
            maxTentativas
        );
    }
}