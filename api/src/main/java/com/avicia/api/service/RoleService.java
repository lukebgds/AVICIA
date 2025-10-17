package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.request.role.RoleRequest;
import com.avicia.api.data.dto.response.role.CriarRoleResponse;
import com.avicia.api.data.dto.response.role.RoleResponse;
import com.avicia.api.data.mapper.RoleMapper;
import com.avicia.api.data.model.Role;
import com.avicia.api.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    
    public CriarRoleResponse criar(RoleRequest dto) {

        // Verifica se a role existe pelo ID
        Role findRole = roleRepository.findById(dto.getIdTipoRole())
            .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        Integer idRoleBase = findRole.getIdRole();

        // Determina a faixa com base no ID da role base
        int faixaMin = (idRoleBase / 100) * 100 + 1;
        int faixaMax = faixaMin + 98;

        // Verifica se a faixa é válida
        if (!isFaixaValida(faixaMin, faixaMax)) {
            throw new IllegalArgumentException(
                "Faixa de IDs inválida. Permitidas apenas: " +
                "[101–199], [301–399], [501–599], [701–799]."
            );
        }

        // Encontra o próximo ID disponível dentro da faixa
        Integer idRole = findNextAvailableId(faixaMin, faixaMax);

        if (idRole == null) {
            throw new IllegalStateException(
                "Não há IDs disponíveis na faixa " + faixaMin + "–" + faixaMax
            );
        }

        Role role = RoleMapper.toEntity(dto, idRole);
        Role salvo = roleRepository.save(role);
        
        return RoleMapper.toCriarResponseDTO(salvo);
    }

    public List<RoleResponse> listarTodos() {

        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<RoleResponse> buscarPorNome(String nome) {
        return roleRepository.findByNome(nome).map(RoleMapper::toResponseDTO);
    }

    public Optional<RoleResponse> atualizar(String nome, RoleRequest dto) {
        
        return roleRepository.findByNome(nome).map(role -> {
            role.setNome(dto.getNome());
            role.setDescricao(dto.getDescricao());
            role.setPermissoes(dto.getPermissoes());

            Role atualizado = roleRepository.save(role);
            return RoleMapper.toResponseDTO(atualizado);
        });
    }

    public boolean deletar(String nome) {
        
        return roleRepository.findByNome(nome).map(role -> {
            roleRepository.delete(role);
            return true;
        }).orElse(false);
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
