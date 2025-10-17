package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.request.usuario.UsuarioRequest;
import com.avicia.api.data.dto.response.usuario.CriarUsuarioResponse;
import com.avicia.api.data.dto.response.usuario.UsuarioResponse;
import com.avicia.api.data.mapper.UsuarioMapper;
import com.avicia.api.data.model.Role;
import com.avicia.api.data.model.Usuario;
import com.avicia.api.repository.RoleRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final Argon2PasswordEncoder passwordEncoder;
    private final SystemLogService systemLogService;

    public CriarUsuarioResponse criar(UsuarioRequest dto) {
        
        Usuario usuario = UsuarioMapper.toEntity(dto);

        // Verifica se a role existe pelo ID
        Role role = roleRepository.findById(dto.getIdRole())
            .orElseThrow(() -> new RuntimeException("Role não encontrada"));
        usuario.setIdRole(role);

        // Geração do ID do usuário: ID da role + número aleatório de 6 dígitos
        String numeroAleatorio = String.format("%06d", new Random().nextInt(1_000_000));
        String idGeradoStr = role.getIdRole() + numeroAleatorio;

        Integer idGerado = Integer.parseInt(idGeradoStr);

        // Garante que o ID seja único
        while (usuarioRepository.existsById(idGerado)) {
            numeroAleatorio = String.format("%06d", new Random().nextInt(1_000_000));
            idGeradoStr = role.getIdRole() + numeroAleatorio;
            idGerado = Integer.parseInt(idGeradoStr);
        }

        usuario.setIdUsuario(idGerado);

        // Criptografia da senha
        usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));

        Usuario salvo = usuarioRepository.save(usuario);

        // Registar o log (Criação)
        systemLogService.registrarCriacao(
                salvo.getIdUsuario(),
                "Usuario",
                "Usuário criado com ID " + salvo.getIdUsuario()
        );

        return UsuarioMapper.toCriarResponseDTO(salvo);
    }

    public List<UsuarioResponse> listarTodos() {
        
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponse> buscaPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf).map(UsuarioMapper::toResponseDTO);
    }

    public Optional<UsuarioResponse> atualizar(String cpf, UsuarioRequest dto) {
        return usuarioRepository.findByCpf(cpf).map(existing -> {

            Usuario usuario = UsuarioMapper.toEntity(dto);
            usuario.setIdUsuario(existing.getIdUsuario());
            usuario.setAtivo(dto.getAtivo());

            Usuario atualizado = usuarioRepository.save(usuario);

            // Resgistro de log (Modificação)
            systemLogService.registrarAtualizacao(
                    atualizado.getIdUsuario(),
                    "Usuario",
                    "Dados do usuário com CPF " + cpf + " foram atualizados"
            );
            return UsuarioMapper.toResponseDTO(atualizado);
        });
    }

    public Optional<UsuarioResponse> atualizarSenha(String cpf, String senhaAtual, String senhaNova) {
        return usuarioRepository.findByCpf(cpf).map(usuario -> {
            
                // Verifica se a senha atual está correta
                if(!passwordEncoder.matches(senhaAtual, usuario.getSenhaHash())) {

                    // Log de erro (senha incorreta)
                    systemLogService.registrarErro(
                        usuario.getIdUsuario(),
                        "Usuario",
                        "Tentativa de alteração de senha falhou — senha atual incorreta"
                    );

                    throw new RuntimeException("Senha atual incorreta!");
                }

                // Criptografa a senha nova
                usuario.setSenhaHash(passwordEncoder.encode(senhaNova));

                Usuario atualizado = usuarioRepository.save(usuario);

                // Resgistro de log (Atualização de senha)
                systemLogService.registrarAtualizacao(
                    atualizado.getIdUsuario(),
                    "Usuario",
                    "Senha alterada com sucesso"
                );

                return UsuarioMapper.toResponseDTO(atualizado);
        });
    }

    public boolean deletar(Integer idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario).map(usuario -> {
            usuarioRepository.delete(usuario);

            // Registro de log (Exclusão)
            systemLogService.registrarExclusao(
                    idUsuario,
                    "Usuario",
                    "Usuário com ID " + idUsuario + " foi deletado"
            );

            return true;
        }).orElse(false);
    }
}
