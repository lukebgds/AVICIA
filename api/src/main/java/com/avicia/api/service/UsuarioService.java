package com.avicia.api.service;

import java.util.List;

import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.data.dto.request.usuario.UsuarioRequest;
import com.avicia.api.data.dto.response.usuario.CriarUsuarioResponse;
import com.avicia.api.data.dto.response.usuario.UsuarioResponse;
import com.avicia.api.data.mapper.UsuarioMapper;
import com.avicia.api.exception.BusinessException;
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
    private final SystemLogService systemLogService;

    @Transactional
    public CriarUsuarioResponse criar(UsuarioRequest dto) {
        
        // Valida CPF
        if (dto.getCpf() == null || dto.getCpf().trim().isEmpty()) {
            throw new BusinessException("CPF não pode ser vazio");
        }
        
        // Verifica se o CPF já existe
        if (usuarioRepository.findByCpf(dto.getCpf()).isPresent()) {
            systemLogService.registrarErro(
                100,
                "Usuario",
                "Tentativa de criar usuário com CPF duplicado: " + dto.getCpf()
            );
            throw new BusinessException("Já existe um usuário cadastrado com o CPF %s", dto.getCpf());
        }
        
        // Valida senha
        if (dto.getSenha() == null || dto.getSenha().trim().isEmpty()) {
            throw new BusinessException("Senha não pode ser vazia");
        }
        
        if (dto.getSenha().length() < 8) {
            throw new BusinessException("Senha deve ter no mínimo 8 caracteres");
        }
        
        Usuario usuario = UsuarioMapper.toEntity(dto);

        // Verifica se a role existe pelo ID
        Role role = roleRepository.findById(dto.getIdRole())
            .orElseThrow(() -> {
                systemLogService.registrarErro(
                    100,
                    "Usuario",
                    "Tentativa de criar usuário com role inexistente: ID " + dto.getIdRole()
                );
                return new BusinessException("Role com ID %d não encontrada", dto.getIdRole());
            });
        
        usuario.setIdRole(role);

        // Geração do ID do usuário: ID da role + número aleatório de 6 dígitos
        Integer idGerado = gerarIdUnico(role.getIdRole());
        
        usuario.setIdUsuario(idGerado);

        // Criptografia da senha
        usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));

        Usuario salvo = usuarioRepository.save(usuario);

        // Registrar o log (Criação)
        systemLogService.registrarCriacao(
                salvo.getIdUsuario(),
                "Usuario",
                "Usuário criado com ID " + salvo.getIdUsuario()
        );

        return UsuarioMapper.toCriarResponseDTO(salvo);
    }

    @Transactional
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioResponse buscaPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .map(UsuarioMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Usuário com CPF %s não encontrado", cpf));
    }

    @Transactional
    public UsuarioResponse atualizar(String cpf, UsuarioRequest dto) {
        
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new BusinessException("CPF não pode ser vazio");
        }
        
        Usuario existing = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new BusinessException("Usuário com CPF %s não encontrado", cpf));

        // Verifica se o novo CPF já existe (se for diferente do atual)
        if (!dto.getCpf().equals(cpf) && usuarioRepository.findByCpf(dto.getCpf()).isPresent()) {
            systemLogService.registrarErro(
                existing.getIdUsuario(),
                "Usuario",
                "Tentativa de atualizar para CPF duplicado: " + dto.getCpf()
            );
            throw new BusinessException("Já existe um usuário cadastrado com o CPF %s", dto.getCpf());
        }

        // Verifica se a role existe
        Role role = roleRepository.findById(dto.getIdRole())
            .orElseThrow(() -> {
                systemLogService.registrarErro(
                    existing.getIdUsuario(),
                    "Usuario",
                    "Tentativa de atualizar usuário com role inexistente: ID " + dto.getIdRole()
                );
                return new BusinessException("Role com ID %d não encontrada", dto.getIdRole());
            });

        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario.setIdUsuario(existing.getIdUsuario());
        usuario.setIdRole(role);
        usuario.setAtivo(dto.getAtivo());
        usuario.setSenhaHash(existing.getSenhaHash()); // Mantém a senha existente

        Usuario atualizado = usuarioRepository.save(usuario);

        // Registro de log (Modificação)
        systemLogService.registrarAtualizacao(
                atualizado.getIdUsuario(),
                "Usuario",
                "Dados do usuário com CPF " + cpf + " foram atualizados"
        );
        
        return UsuarioMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public UsuarioResponse atualizarSenha(String cpf, String senhaAtual, String senhaNova) {
        
        // FAZER COMPRESSÃO DISSO DEPOIS

        if (cpf == null || cpf.trim().isEmpty()) {
            throw new BusinessException("CPF não pode ser vazio");
        }
        
        if (senhaAtual == null || senhaAtual.trim().isEmpty()) {
            throw new BusinessException("Senha atual não pode ser vazia");
        }
        
        if (senhaNova == null || senhaNova.trim().isEmpty()) {
            throw new BusinessException("Nova senha não pode ser vazia");
        }
        
        if (senhaNova.length() < 8) {
            throw new BusinessException("Nova senha deve ter no mínimo 8 caracteres");
        }
        
        if (senhaAtual.equals(senhaNova)) {
            throw new BusinessException("A nova senha deve ser diferente da senha atual");
        }
        
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new BusinessException("Usuário com CPF %s não encontrado", cpf));
        
        // Verifica se a senha atual está correta
        if(!passwordEncoder.matches(senhaAtual, usuario.getSenhaHash())) {
            // Log de erro (senha incorreta)
            systemLogService.registrarErro(
                usuario.getIdUsuario(),
                "Usuario",
                "Tentativa de alteração de senha falhou — senha atual incorreta"
            );
            throw new BusinessException("Senha atual incorreta");
        }

        // Criptografa a senha nova
        usuario.setSenhaHash(passwordEncoder.encode(senhaNova));

        Usuario atualizado = usuarioRepository.save(usuario);

        // Registro de log (Atualização de senha)
        systemLogService.registrarAtualizacao(
            atualizado.getIdUsuario(),
            "Usuario",
            "Senha alterada com sucesso"
        );

        return UsuarioMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer idUsuario) {
        
        if (idUsuario == null) {
            throw new BusinessException("ID do usuário não pode ser nulo");
        }
        
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new BusinessException("Usuário com ID %d não encontrado", idUsuario));
        
        usuarioRepository.delete(usuario);

        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
                idUsuario,
                "Usuario",
                "Usuário com ID " + idUsuario + " foi deletado"
        );
    }


    // ================= MÉTODOS AUXILIARES ================= //
    
    private Integer gerarIdUnico(Integer idRole) {
        int tentativas = 0;
        int maxTentativas = 1000;
        
        while (tentativas < maxTentativas) {
            String numeroAleatorio = String.format("%06d", new Random().nextInt(1_000_000));
            String idGeradoStr = idRole + numeroAleatorio;
            Integer idGerado = Integer.parseInt(idGeradoStr);
            
            if (!usuarioRepository.existsById(idGerado)) {
                return idGerado;
            }
            tentativas++;
        }
        
        systemLogService.registrarErro(
            100,
            "Usuario",
            "Não foi possível gerar ID único após " + maxTentativas + " tentativas"
        );
        throw new BusinessException("Não foi possível gerar um ID único para o usuário. Tente novamente.");
    }

}
