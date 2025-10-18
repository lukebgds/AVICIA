package com.avicia.api.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.dto.request.funcionario.FuncionarioRequest;
import com.avicia.api.data.dto.response.funcionario.FuncionarioResponse;
import com.avicia.api.data.mapper.FuncionarioMapper;
import com.avicia.api.exception.BusinessException;
import com.avicia.api.model.Funcionario;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.FuncionarioRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    
    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final SystemLogService systemLogService;
    
    @Transactional
    public FuncionarioResponse criar(FuncionarioRequest dto) {
        
        // Validações
        if (dto.getIdUsuario() == null) {
            throw new BusinessException("ID do usuário não pode ser nulo");
        }
        
        if (dto.getMatricula() == null || dto.getMatricula().trim().isEmpty()) {
            throw new BusinessException("Matrícula não pode ser vazia");
        }
        
        // Verifica se a matrícula já existe
        if (funcionarioRepository.findByMatricula(dto.getMatricula()).isPresent()) {
            systemLogService.registrarErro(
                null,
                "Funcionario",
                "Tentativa de criar funcionário com matrícula duplicada: " + dto.getMatricula()
            );
            throw new BusinessException("Já existe um funcionário cadastrado com a matrícula %s", dto.getMatricula());
        }
        
        Funcionario funcionario = FuncionarioMapper.toEntity(dto);
        
        // Recupera o usuário
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> {
                    systemLogService.registrarErro(
                        null,
                        "Funcionario",
                        "Tentativa de criar funcionário com usuário inexistente: ID " + dto.getIdUsuario()
                    );
                    return new BusinessException("Usuário com ID %d não encontrado", dto.getIdUsuario());
                });
        
        funcionario.setUsuario(usuario);
        
        // Geração do ID do Funcionário
        Integer idFuncionario = gerarIdUnicoFuncionario(usuario.getIdUsuario());
        funcionario.setIdFuncionario(idFuncionario);
        
        Funcionario salvo = funcionarioRepository.save(funcionario);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            salvo.getIdFuncionario(),
            "Funcionario",
            "Funcionário criado com ID " + salvo.getIdFuncionario() + " e matrícula " + salvo.getMatricula()
        );
        
        return FuncionarioMapper.toResponseDTO(salvo);
    }
    
    @Transactional
    public List<FuncionarioResponse> listarTodos() {
        return funcionarioRepository.findAll()
                .stream()
                .map(FuncionarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public FuncionarioResponse buscarPorId(Integer idFuncionario) {
        if (idFuncionario == null) {
            throw new BusinessException("ID do funcionário não pode ser nulo");
        }
        
        return funcionarioRepository.findById(idFuncionario)
                .map(FuncionarioMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Funcionário com ID %d não encontrado", idFuncionario));
    }
    
    @Transactional
    public FuncionarioResponse buscarPorMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new BusinessException("Matrícula não pode ser vazia");
        }
        
        return funcionarioRepository.findByMatricula(matricula)
                .map(FuncionarioMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Funcionário com matrícula %s não encontrado", matricula));
    }
    
    @Transactional
    public FuncionarioResponse atualizar(String matricula, FuncionarioRequest dto) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new BusinessException("Matrícula não pode ser vazia");
        }
        
        if (dto.getIdUsuario() == null) {
            throw new BusinessException("ID do usuário não pode ser nulo");
        }
        
        Funcionario funcionario = funcionarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new BusinessException("Funcionário com matrícula %s não encontrado", matricula));
        
        // Verifica se a nova matrícula já existe (se for diferente da atual)
        if (!dto.getMatricula().equals(matricula) && funcionarioRepository.findByMatricula(dto.getMatricula()).isPresent()) {
            systemLogService.registrarErro(
                funcionario.getIdFuncionario(),
                "Funcionario",
                "Tentativa de atualizar para matrícula duplicada: " + dto.getMatricula()
            );
            throw new BusinessException("Já existe um funcionário cadastrado com a matrícula %s", dto.getMatricula());
        }
        
        funcionario.setCargo(dto.getCargo());
        funcionario.setSetor(dto.getSetor());
        funcionario.setMatricula(dto.getMatricula());
        funcionario.setObservacoes(dto.getObservacoes());
        
        // Atualiza o usuário vinculado
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> {
                    systemLogService.registrarErro(
                        funcionario.getIdFuncionario(),
                        "Funcionario",
                        "Tentativa de atualizar funcionário com usuário inexistente: ID " + dto.getIdUsuario()
                    );
                    return new BusinessException("Usuário com ID %d não encontrado", dto.getIdUsuario());
                });
        
        funcionario.setUsuario(usuario);
        
        Funcionario atualizado = funcionarioRepository.save(funcionario);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            atualizado.getIdFuncionario(),
            "Funcionario",
            "Dados do funcionário com matrícula " + matricula + " foram atualizados"
        );
        
        return FuncionarioMapper.toResponseDTO(atualizado);
    }
    
    @Transactional
    public void deletar(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new BusinessException("Matrícula não pode ser vazia");
        }
        
        Funcionario funcionario = funcionarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new BusinessException("Funcionário com matrícula %s não encontrado", matricula));
        
        Integer idFuncionario = funcionario.getIdFuncionario();
        
        funcionarioRepository.delete(funcionario);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            idFuncionario,
            "Funcionario",
            "Funcionário com matrícula " + matricula + " foi deletado"
        );
    }
    
    // ================= MÉTODOS AUXILIARES ================= //
    
    private Integer gerarIdUnicoFuncionario(Integer idUsuario) {
        int tentativas = 0;
        int maxTentativas = 1000;
        
        String idUsuarioStr = String.valueOf(idUsuario);
        
        if (idUsuarioStr.length() < 3) {
            throw new BusinessException("ID do usuário inválido para geração de ID do funcionário");
        }
        
        String prefixo = idUsuarioStr.substring(0, 3);
        
        while (tentativas < maxTentativas) {
            String numeroAleatorio = String.format("%03d", new Random().nextInt(1_000));
            Integer idFuncionario = Integer.parseInt(prefixo + numeroAleatorio);
            
            if (!funcionarioRepository.existsById(idFuncionario)) {
                return idFuncionario;
            }
            tentativas++;
        }
        
        systemLogService.registrarErro(
            null,
            "Funcionario",
            "Não foi possível gerar ID único para funcionário após " + maxTentativas + " tentativas"
        );
        throw new BusinessException("Não foi possível gerar um ID único para o funcionário. Tente novamente.");
    }
}