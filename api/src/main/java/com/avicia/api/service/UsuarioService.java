package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.request.usuario.UsuarioRequest;
import com.avicia.api.data.dto.response.usuario.CriarUsuarioResponse;
import com.avicia.api.data.dto.response.usuario.UsuarioResponse;
import com.avicia.api.data.mapper.UsuarioMapper;
import com.avicia.api.model.Role;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.RoleRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final Argon2PasswordEncoder passwordEncoder;

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
            return UsuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
        });
    }

    public Optional<UsuarioResponse> atualizarSenha(String cpf, String senhaAtual, String senhaNova) {
        return usuarioRepository.findByCpf(cpf).map(usuario -> {
            
                // Verifica se a senha atual está correta
                if(!passwordEncoder.matches(senhaAtual, usuario.getSenhaHash())) {
                    throw new RuntimeException("Senha atual incorreta!");
                }

                // Criptografa a senha nova
                usuario.setSenhaHash(passwordEncoder.encode(senhaNova));

                Usuario atualizado = usuarioRepository.save(usuario);

                return UsuarioMapper.toResponseDTO(atualizado);
        });
    }

    public boolean deletar(Integer idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario).map(usuario -> {
            usuarioRepository.delete(usuario);
            return true;
        }).orElse(false);
    }
}
