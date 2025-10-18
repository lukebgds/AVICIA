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
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemLogService systemLogService;

    @Transactional
    public List<PacienteResponse> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(PacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PacienteResponse buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("ID do paciente não pode ser nulo");
        }
        
        return pacienteRepository.findById(id)
                .map(PacienteMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Paciente com ID %d não encontrado", id));
    }

    @Transactional
    public PacienteResponse buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new BusinessException("CPF não pode ser vazio");
        }
        
        return pacienteRepository.findByUsuario_Cpf(cpf)
                .map(PacienteMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Paciente com CPF %s não encontrado", cpf));
    }

    @Transactional
    public PacienteResponse criar(PacienteRequest dto) {
        // Validações
        if (dto.getIdUsuario() == null) {
            throw new BusinessException("ID do usuário não pode ser nulo");
        }
        
        Paciente paciente = PacienteMapper.toEntity(dto);

        // Recupera o usuário
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
            .orElseThrow(() -> {
                systemLogService.registrarErro(
                    null,
                    "Paciente",
                    "Tentativa de criar paciente com usuário inexistente: ID " + dto.getIdUsuario()
                );
                return new BusinessException("Usuário com ID %d não encontrado", dto.getIdUsuario());
            });
        
        // Verifica se já existe um paciente para este usuário
        if (pacienteRepository.findByUsuario_Cpf(usuario.getCpf()).isPresent()) {
            systemLogService.registrarErro(
                null,
                "Paciente",
                "Tentativa de criar paciente duplicado para usuário: " + usuario.getCpf()
            );
            throw new BusinessException("Já existe um paciente cadastrado para o usuário com CPF %s", usuario.getCpf());
        }
        
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
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new BusinessException("CPF não pode ser vazio");
        }
        
        if (dto.getIdUsuario() == null) {
            throw new BusinessException("ID do usuário não pode ser nulo");
        }
        
        Paciente existing = pacienteRepository.findByUsuario_Cpf(cpf)
                .orElseThrow(() -> new BusinessException("Paciente com CPF %s não encontrado", cpf));
        
        // Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
            .orElseThrow(() -> {
                systemLogService.registrarErro(
                    existing.getIdPaciente(),
                    "Paciente",
                    "Tentativa de atualizar paciente com usuário inexistente: ID " + dto.getIdUsuario()
                );
                return new BusinessException("Usuário com ID %d não encontrado", dto.getIdUsuario());
            });
        
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
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new BusinessException("CPF não pode ser vazio");
        }
        
        Paciente existing = pacienteRepository.findByUsuario_Cpf(cpf)
                .orElseThrow(() -> new BusinessException("Paciente com CPF %s não encontrado", cpf));
        
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
        
        String idUsuarioStr = String.valueOf(idUsuario);
        
        if (idUsuarioStr.length() < 3) {
            throw new BusinessException("ID do usuário inválido para geração de ID do paciente");
        }
        
        String prefixo = idUsuarioStr.substring(0, 3);
        
        while (tentativas < maxTentativas) {
            String numeroAleatorio = String.format("%03d", new Random().nextInt(1_000));
            Integer idPaciente = Integer.parseInt(prefixo + numeroAleatorio);
            
            if (!pacienteRepository.existsById(idPaciente)) {
                return idPaciente;
            }
            tentativas++;
        }
        
        systemLogService.registrarErro(
            null,
            "Paciente",
            "Não foi possível gerar ID único para paciente após " + maxTentativas + " tentativas"
        );
        throw new BusinessException("Não foi possível gerar um ID único para o paciente. Tente novamente.");
    }
}