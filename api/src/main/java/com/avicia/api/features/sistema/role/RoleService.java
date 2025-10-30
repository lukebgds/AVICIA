package com.avicia.api.features.sistema.role;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.sistema.role.request.RoleRequest;
import com.avicia.api.features.sistema.role.response.CriarRoleResponse;
import com.avicia.api.features.sistema.role.response.RoleResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;
    private final SystemLogService systemLogService;
    private final VerificarRole verificarRole;
    
    @Transactional
    public CriarRoleResponse criar(RoleRequest dto) {
        
        // Validações
        verificarRole.validarNomeNaoVazio(dto.getNome());
        verificarRole.verificarNomeDuplicado(dto.getNome());
        
        // Verifica se a role base existe pelo ID
        Role findRole = verificarRole.buscarRolePorId(dto.getIdTipoRole());
        Integer idRoleBase = findRole.getIdRole();
        
        // Determina a faixa com base no ID da role base
        int[] faixa = verificarRole.calcularFaixa(idRoleBase);
        int faixaMin = faixa[0];
        int faixaMax = faixa[1];
        
        // Verifica se a faixa é válida
        verificarRole.validarFaixaValida(faixaMin, faixaMax);
        
        // Encontra o próximo ID disponível dentro da faixa
        Integer idRole = verificarRole.findNextAvailableId(faixaMin, faixaMax);
        verificarRole.validarIdDisponivel(idRole, faixaMin, faixaMax);
        
        Role role = RoleMapper.toEntity(dto, idRole);
        Role salvo = roleRepository.save(role);

        // Encontrar o ID do Usuário pelo token de sessão
        Integer id = verificarRole.getIdUsuarioToken();
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            id,
            "Role",
            "Role criada com ID " + salvo.getIdRole() + " e nome '" + salvo.getNome() + "'"
        );
        
        return RoleMapper.toCriarResponseDTO(salvo);
    }
    
    @Transactional(readOnly = true)
    public List<RoleResponse> listarTodos() {
        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public RoleResponse buscarPorNome(String nome) {
        Role role = verificarRole.buscarRolePorNome(nome);
        return RoleMapper.toResponseDTO(role);
    }
    
    @Transactional
    public RoleResponse atualizar(String nome, RoleRequest dto) {
        
        verificarRole.validarNomeNaoVazio(nome);
        verificarRole.validarNovoNomeNaoVazio(dto.getNome());
        
        Role role = verificarRole.buscarRolePorNome(nome);
        
        // Verifica se o novo nome já existe (se for diferente do atual)
        verificarRole.verificarNomeDuplicadoAtualizacao(nome, dto.getNome(), role.getIdRole());
        
        role.setNome(dto.getNome());
        role.setDescricao(dto.getDescricao());
        role.setPermissoes(dto.getPermissoes());
        
        Role atualizado = roleRepository.save(role);

        // Encontrar o ID do Usuário pelo token de sessão
        Integer id = verificarRole.getIdUsuarioToken();
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            id,
            "Role",
            "Role '" + nome + "' foi atualizada para '" + dto.getNome() + "'"
        );
        
        return RoleMapper.toResponseDTO(atualizado);
    }
    
    @Transactional
    public void deletar(String nome) {
        
        verificarRole.validarNomeNaoVazio(nome);
        
        Role role = verificarRole.buscarRolePorNome(nome);
        Integer idRole = role.getIdRole();
        
        roleRepository.delete(role);

        // Encontrar o ID do Usuário pelo token de sessão
        Integer id = verificarRole.getIdUsuarioToken();
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            id,
            "Role",
            "Role '" + nome + "' com ID " + idRole + " foi deletada"
        );
    }
}