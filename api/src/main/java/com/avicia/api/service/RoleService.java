package com.avicia.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.dto.request.role.RoleRequest;
import com.avicia.api.data.dto.response.role.CriarRoleResponse;
import com.avicia.api.data.dto.response.role.RoleResponse;
import com.avicia.api.data.mapper.RoleMapper;
import com.avicia.api.exception.BusinessException;
import com.avicia.api.model.Role;
import com.avicia.api.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;
    private final SystemLogService systemLogService;
    
    @Transactional
    public CriarRoleResponse criar(RoleRequest dto) {
        // Valida nome
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome da role não pode ser vazio");
        }
        
        // Verifica se já existe uma role com o mesmo nome
        if (roleRepository.findByNome(dto.getNome()).isPresent()) {
            systemLogService.registrarErro(
                null,
                "Role",
                "Tentativa de criar role com nome duplicado: " + dto.getNome()
            );
            throw new BusinessException("Já existe uma role com o nome '%s'", dto.getNome());
        }
        
        // Verifica se a role base existe pelo ID
        Role findRole = roleRepository.findById(dto.getIdTipoRole())
            .orElseThrow(() -> {
                systemLogService.registrarErro(
                    null,
                    "Role",
                    "Tentativa de criar role com tipo inexistente: ID " + dto.getIdTipoRole()
                );
                return new BusinessException("Role base com ID %d não encontrada", dto.getIdTipoRole());
            });
        
        Integer idRoleBase = findRole.getIdRole();
        
        // Determina a faixa com base no ID da role base
        int faixaMin = (idRoleBase / 100) * 100 + 1;
        int faixaMax = faixaMin + 98;
        
        // Verifica se a faixa é válida
        if (!isFaixaValida(faixaMin, faixaMax)) {
            // Log de erro (faixa inválida)
            systemLogService.registrarErro(
                null,
                "Role",
                "Tentativa de criar role com faixa inválida: [" + faixaMin + "–" + faixaMax + "]"
            );
            
            throw new BusinessException(
                "Faixa de IDs inválida [%d–%d]. Permitidas apenas: [101–199], [301–399], [501–599], [701–799]",
                faixaMin, faixaMax
            );
        }
        
        // Encontra o próximo ID disponível dentro da faixa
        Integer idRole = findNextAvailableId(faixaMin, faixaMax);
        
        if (idRole == null) {
            // Log de erro (sem IDs disponíveis)
            systemLogService.registrarErro(
                null,
                "Role",
                "Não há IDs disponíveis na faixa [" + faixaMin + "–" + faixaMax + "]"
            );
            
            throw new BusinessException(
                "Não há IDs disponíveis na faixa %d–%d",
                faixaMin, faixaMax
            );
        }
        
        Role role = RoleMapper.toEntity(dto, idRole);
        Role salvo = roleRepository.save(role);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            salvo.getIdRole(),
            "Role",
            "Role criada com ID " + salvo.getIdRole() + " e nome '" + salvo.getNome() + "'"
        );
        
        return RoleMapper.toCriarResponseDTO(salvo);
    }
    
    @Transactional
    public List<RoleResponse> listarTodos() {
        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public RoleResponse buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new BusinessException("Nome da role não pode ser vazio");
        }
        
        return roleRepository.findByNome(nome)
                .map(RoleMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Role com nome '%s' não encontrada", nome));
    }
    
    @Transactional
    public RoleResponse atualizar(String nome, RoleRequest dto) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new BusinessException("Nome da role não pode ser vazio");
        }
        
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new BusinessException("Novo nome da role não pode ser vazio");
        }
        
        Role role = roleRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException("Role com nome '%s' não encontrada", nome));
        
        // Verifica se o novo nome já existe (se for diferente do atual)
        if (!dto.getNome().equals(nome) && roleRepository.findByNome(dto.getNome()).isPresent()) {
            systemLogService.registrarErro(
                role.getIdRole(),
                "Role",
                "Tentativa de atualizar para nome duplicado: " + dto.getNome()
            );
            throw new BusinessException("Já existe uma role com o nome '%s'", dto.getNome());
        }
        
        role.setNome(dto.getNome());
        role.setDescricao(dto.getDescricao());
        role.setPermissoes(dto.getPermissoes());
        
        Role atualizado = roleRepository.save(role);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            atualizado.getIdRole(),
            "Role",
            "Role '" + nome + "' foi atualizada para '" + dto.getNome() + "'"
        );
        
        return RoleMapper.toResponseDTO(atualizado);
    }
    
    @Transactional
    public void deletar(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new BusinessException("Nome da role não pode ser vazio");
        }
        
        Role role = roleRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException("Role com nome '%s' não encontrada", nome));
        
        Integer idRole = role.getIdRole();
        
        roleRepository.delete(role);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            idRole,
            "Role",
            "Role '" + nome + "' com ID " + idRole + " foi deletada"
        );
    }
    
    // ================= MÉTODOS AUXILIARES ================= //
    
    // Verifica se a faixa é uma das permitidas.
    private boolean isFaixaValida(int faixaMin, int faixaMax) {
        return (faixaMin == 101 && faixaMax == 199)
            || (faixaMin == 301 && faixaMax == 399)
            || (faixaMin == 501 && faixaMax == 599)
            || (faixaMin == 701 && faixaMax == 799);
    }
    
    // Procura o próximo ID livre dentro da faixa.
    private Integer findNextAvailableId(int faixaMin, int faixaMax) {
        for (int id = faixaMin; id <= faixaMax; id++) {
            if (!roleRepository.existsById(id)) {
                return id;
            }
        }
        return null; // Nenhum ID disponível
    }
}