package com.avicia.api.security.verify;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.model.Role;
import com.avicia.api.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarRole {

    private final RoleRepository roleRepository;
    private final SystemError systemError;

    public void validarNomeNaoVazio(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            systemError.error("Nome da role não pode ser vazio");
        }
    }

    public void validarNovoNomeNaoVazio(String novoNome) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            systemError.error("Novo nome da role não pode ser vazio");
        }
    }

    public void verificarNomeDuplicado(String nome) {
        if (roleRepository.findByNome(nome).isPresent()) {
            systemError.error(100, "Role", "Já existe uma role com o nome '%s'", nome);
        }
    }

    public void verificarNomeDuplicadoAtualizacao(String nomeAtual, String novoNome, Integer idRole) {
        if (!novoNome.equals(nomeAtual) && roleRepository.findByNome(novoNome).isPresent()) {
            systemError.error(idRole, "Role", "Já existe uma role com o nome '%s'", novoNome);
        }
    }

    public Role buscarRolePorId(Integer idRole) {
        var role = roleRepository.findById(idRole).orElse(null);
        if (role == null) {
            systemError.error(100, "Role", "Role base com ID %d não encontrada", idRole);
        }
        return role;
    }

    public Role buscarRolePorNome(String nome) {
        validarNomeNaoVazio(nome);
        var role = roleRepository.findByNome(nome).orElse(null);
        if (role == null) {
            systemError.error("Role com nome '%s' não encontrada", nome);
        }
        return role;
    }

    public void validarFaixaValida(int faixaMin, int faixaMax) {
        if (!isFaixaValida(faixaMin, faixaMax)) {
            systemError.error(
                100,
                "Role",
                "Faixa de IDs inválida [%d–%d]. Permitidas apenas: [101–199], [301–399], [501–599], [701–799]",
                faixaMin, faixaMax
            );
        }
    }

    private boolean isFaixaValida(int faixaMin, int faixaMax) {
        return (faixaMin == 101 && faixaMax == 199)
            || (faixaMin == 301 && faixaMax == 399)
            || (faixaMin == 501 && faixaMax == 599)
            || (faixaMin == 701 && faixaMax == 799);
    }

    public Integer findNextAvailableId(int faixaMin, int faixaMax) {
        for (int id = faixaMin; id <= faixaMax; id++) {
            if (!roleRepository.existsById(id)) {
                return id;
            }
        }
        return null;
    }

    public void validarIdDisponivel(Integer idRole, int faixaMin, int faixaMax) {
        if (idRole == null) {
            systemError.error(
                100,
                "Role",
                "Não há IDs disponíveis na faixa %d–%d",
                faixaMin, faixaMax
            );
        }
    }

    public int[] calcularFaixa(Integer idRoleBase) {
        int faixaMin = (idRoleBase / 100) * 100 + 1;
        int faixaMax = faixaMin + 98;
        return new int[]{faixaMin, faixaMax};
    }
}
