package com.avicia.api.security.verify;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

import com.avicia.api.exception.SystemError;
import com.avicia.api.model.Role;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.RoleRepository;
import com.avicia.api.repository.UsuarioRepository;
import com.avicia.api.util.Throwing;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificarUsuario {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final Argon2PasswordEncoder passwordEncoder;
    private final SystemError systemError;

    /**
     * Valida se o CPF não é nulo ou vazio
     */
    public void validarCpfNaoVazio(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            systemError.error("CPF não pode ser vazio");
        }
    }

    /**
     * Verifica se o CPF já existe no banco de dados
     */
    public void verificarCpfDuplicado(String cpf, Integer idUsuario) {
        if (usuarioRepository.findByCpf(cpf).isPresent()) {
            systemError.error(idUsuario, "Usuario", "Já existe um usuário cadastrado com o CPF %s", cpf);
        }
    }

    /**
     * Verifica se o novo CPF já existe (exceto para o próprio usuário)
     */
    public void verificarCpfDuplicadoAtualizacao(String cpfAtual, String cpfNovo, Integer idUsuario) {
        if (!cpfNovo.equals(cpfAtual) && usuarioRepository.findByCpf(cpfNovo).isPresent()) {
            systemError.error(idUsuario, "Usuario", "Já existe um usuário cadastrado com o CPF %s", cpfNovo);
        }
    }

    /**
     * Valida senha completa com todos os requisitos de segurança
     */
    public void validarSenha(String senha) {

        if (senha == null || senha.trim().isEmpty()) { 
            systemError.error("Senha não pode ser vazia");
        }
        if (senha.length() < 8) { 
            systemError.error("Senha deve ter no mínimo 8 caracteres");
        }
        if (!senha.matches(".*[A-Z].*")) { 
            systemError.error("Senha deve conter pelo menos uma letra maiúscula");
        }
        if (!senha.matches(".*[a-z].*")) { 
            systemError.error("Senha deve conter pelo menos uma letra minúscula");
        }
        if (!senha.matches(".*\\d.*")) { 
            systemError.error("Senha deve conter pelo menos um número");
        }
        if (!senha.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            systemError.error("Senha deve conter pelo menos um caractere especial"); 
        }
    }

    /**
     * Valida que a nova senha é diferente da atual
     */
    public void validarSenhaDiferente(String senhaAtual, String senhaNova) {
        if (senhaAtual.equals(senhaNova)) {
            systemError.error("A nova senha deve ser diferente da senha atual");
        }
    }

    /**
     * Verifica se a role existe pelo ID
     */
    public Role verificarRoleExiste(Integer idRole, Integer idUsuario) {
        return roleRepository.findById(idRole)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error(idUsuario, "Usuario", "Role com ID %d não encontrada", idRole)
            ));
    }

    /**
     * Busca usuário por CPF ou lança exceção
     */
    public Usuario buscarUsuarioPorCpf(String cpf) {
        validarCpfNaoVazio(cpf);
        return usuarioRepository.findByCpf(cpf)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error("Usuário com CPF %s não encontrado", cpf)
            ));
    }

    /**
     * Busca usuário por ID ou lança exceção
     */
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        if (idUsuario == null) {
            systemError.error("ID do usuário não pode ser nulo");
        }

        return usuarioRepository.findByIdUsuario(idUsuario)
            .orElseThrow(Throwing.supplier(() ->
                systemError.error(idUsuario, "Usuario", "Usuário com ID %d não encontrado", idUsuario)
            ));
    }

    /**
     * Verifica se a senha atual está correta
     */
    public void verificarSenhaAtual(String senhaFornecida, String senhaHash, Integer idUsuario) {
        if (!passwordEncoder.matches(senhaFornecida, senhaHash)) {
            systemError.error(idUsuario, "Usuario", "Senha atual incorreta");
        }
    }

    /**
     * Verifica se um ID de usuário já existe
     */
    public boolean idUsuarioExiste(Integer idUsuario) {
        return usuarioRepository.existsById(idUsuario);
    }

}
