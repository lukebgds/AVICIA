package com.avicia.api.features.sistema.role;

import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarRole {

    private final RoleRepository roleRepository;
    private final SystemError systemError;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    public void validarNomeNaoVazio(String nome) {
        Integer id = getIdUsuarioToken();
        if (nome == null || nome.trim().isEmpty()) {
            systemError.error(id, "Role", "Nome da role não pode ser vazio");
        }
    }

    public void validarNovoNomeNaoVazio(String novoNome) {
        Integer id = getIdUsuarioToken();
        if (novoNome == null || novoNome.trim().isEmpty()) {
            systemError.error(id, "Role", "Novo nome da role não pode ser vazio");
        }
    }

    public void verificarNomeDuplicado(String nome) {
        Integer id = getIdUsuarioToken();
        if (roleRepository.findByNome(nome).isPresent()) {
            systemError.error(id, "Role", "Já existe uma role com o nome '%s'", nome);
        }
    }

    public void verificarNomeDuplicadoAtualizacao(String nomeAtual, String novoNome, Integer idRole) {
        Integer id = getIdUsuarioToken();
        if (!novoNome.equals(nomeAtual) && roleRepository.findByNome(novoNome).isPresent()) {
            systemError.error(id, "Role", "Já existe uma role com o nome '%s'", novoNome);
        }
    }

    public Role buscarRolePorId(Integer idRole) {
        Integer id = getIdUsuarioToken();
        var role = roleRepository.findById(idRole).orElse(null);
        if (role == null) {
            systemError.error(id, "Role", "Role base com ID %d não encontrada", idRole);
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
        Integer id = getIdUsuarioToken();
        if (!isFaixaValida(faixaMin, faixaMax)) {
            systemError.error(
                id,
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
        Integer id = getIdUsuarioToken();
        if (idRole == null) {
            systemError.error(
                id,
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

    protected Integer getIdUsuarioToken() {
        return usuarioAutenticadoUtil.getIdUsuario();
    }
}
