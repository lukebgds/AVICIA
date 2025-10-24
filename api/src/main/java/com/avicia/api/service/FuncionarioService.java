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
import com.avicia.api.security.verify.VerificarFuncionario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    
    private final FuncionarioRepository funcionarioRepository;
    private final SystemLogService systemLogService;
    private final VerificarFuncionario verificarFuncionario;
    
    @Transactional
    public FuncionarioResponse criar(FuncionarioRequest dto) {
        // Validações
        verificarFuncionario.validarIdUsuarioNaoNulo(dto.getIdUsuario());
        verificarFuncionario.validarMatriculaNaoVazia(dto.getMatricula());
        
        // Verifica se a matrícula já existe
        verificarFuncionario.verificarMatriculaDuplicada(dto.getMatricula());
        
        Funcionario funcionario = FuncionarioMapper.toEntity(dto);
        
        // Recupera o usuário
        Usuario usuario = verificarFuncionario.buscarUsuarioPorId(dto.getIdUsuario());
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
    
    @Transactional(readOnly = true)
    public List<FuncionarioResponse> listarTodos() {
        return funcionarioRepository.findAll()
                .stream()
                .map(FuncionarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public FuncionarioResponse buscarPorId(Integer idFuncionario) {
        Funcionario funcionario = verificarFuncionario.buscarFuncionarioPorId(idFuncionario);
        return FuncionarioMapper.toResponseDTO(funcionario);
    }
    
    @Transactional(readOnly = true)
    public FuncionarioResponse buscarPorMatricula(String matricula) {
        Funcionario funcionario = verificarFuncionario.buscarFuncionarioPorMatricula(matricula);
        return FuncionarioMapper.toResponseDTO(funcionario);
    }
    
    @Transactional
    public FuncionarioResponse atualizar(String matricula, FuncionarioRequest dto) {
        verificarFuncionario.validarMatriculaNaoVazia(matricula);
        verificarFuncionario.validarIdUsuarioNaoNulo(dto.getIdUsuario());
        
        Funcionario funcionario = verificarFuncionario.buscarFuncionarioPorMatricula(matricula);
        
        // Verifica se a nova matrícula já existe (se for diferente da atual)
        verificarFuncionario.verificarMatriculaDuplicadaAtualizacao(
            matricula,
            dto.getMatricula(),
            funcionario.getIdFuncionario()
        );
        
        funcionario.setCargo(dto.getCargo());
        funcionario.setSetor(dto.getSetor());
        funcionario.setMatricula(dto.getMatricula());
        funcionario.setObservacoes(dto.getObservacoes());
        
        // Atualiza o usuário vinculado
        Usuario usuario = verificarFuncionario.buscarUsuarioPorIdAtualizacao(
            dto.getIdUsuario(),
            funcionario.getIdFuncionario()
        );
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
        verificarFuncionario.validarMatriculaNaoVazia(matricula);
        
        Funcionario funcionario = verificarFuncionario.buscarFuncionarioPorMatricula(matricula);
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
        
        verificarFuncionario.validarIdUsuarioParaGeracao(idUsuario);
        
        String idUsuarioStr = String.valueOf(idUsuario);
        String prefixo = idUsuarioStr.substring(0, 3);
        
        while (tentativas < maxTentativas) {
            String numeroAleatorio = String.format("%03d", new Random().nextInt(1_000));
            Integer idFuncionario = Integer.parseInt(prefixo + numeroAleatorio);
            
            if (!verificarFuncionario.idFuncionarioExiste(idFuncionario)) {
                return idFuncionario;
            }
            tentativas++;
        }
        
        // Se chegou aqui, não conseguiu gerar ID único após todas as tentativas
        throw new BusinessException(
            100,
            "Funcionario",
            "Não foi possível gerar um ID único para o funcionário após %d tentativas. Tente novamente.",
            maxTentativas
        );
    }
}