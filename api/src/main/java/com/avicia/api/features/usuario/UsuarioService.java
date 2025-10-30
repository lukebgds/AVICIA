package com.avicia.api.features.usuario;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.exception.BusinessException;
import com.avicia.api.features.sistema.role.Role;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.features.usuario.request.UsuarioRequest;
import com.avicia.api.features.usuario.response.CriarUsuarioResponse;
import com.avicia.api.features.usuario.response.UsuarioResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final Argon2PasswordEncoder passwordEncoder;
    private final SystemLogService systemLogService;
    private final VerificarUsuario verificarUsuario;

    @Transactional
    public CriarUsuarioResponse criar(UsuarioRequest dto) {
        
        // Validações
        verificarUsuario.validarCpfNaoVazio(dto.getCpf());
        verificarUsuario.verificarCpfDuplicado(dto.getCpf(), null);
        verificarUsuario.validarSenha(dto.getSenha());
        
        Usuario usuario = UsuarioMapper.toEntity(dto);

        // Verifica se a role existe
        Role role = verificarUsuario.verificarRoleExiste(dto.getIdRole(), null);
        usuario.setIdRole(role);

        // Geração do ID do usuário
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
        Usuario usuario = verificarUsuario.buscarUsuarioPorCpf(cpf);
        return UsuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponse atualizar(String cpf, UsuarioRequest dto) {
        
        verificarUsuario.validarCpfNaoVazio(cpf);
        
        Usuario existing = verificarUsuario.buscarUsuarioPorCpf(cpf);

        // Verifica se o novo CPF já existe (se for diferente do atual)
        verificarUsuario.verificarCpfDuplicadoAtualizacao(cpf, dto.getCpf(), existing.getIdUsuario());

        // Verifica se a role existe
        Role role = verificarUsuario.verificarRoleExiste(dto.getIdRole(), existing.getIdUsuario());

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
        
        verificarUsuario.validarCpfNaoVazio(cpf);
        verificarUsuario.validarSenha(senhaAtual);
        verificarUsuario.validarSenha(senhaNova);
        verificarUsuario.validarSenhaDiferente(senhaAtual, senhaNova);
        
        Usuario usuario = verificarUsuario.buscarUsuarioPorCpf(cpf);
        
        // Verifica se a senha atual está correta
        verificarUsuario.verificarSenhaAtual(senhaAtual, usuario.getSenhaHash(), usuario.getIdUsuario());

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
        
        Usuario usuario = verificarUsuario.buscarUsuarioPorId(idUsuario);
        
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
            
            if (!verificarUsuario.idUsuarioExiste(idGerado)) {
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