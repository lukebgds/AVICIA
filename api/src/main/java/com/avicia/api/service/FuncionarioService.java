package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.request.FuncionarioRequest;
import com.avicia.api.data.dto.response.FuncionarioResponse;
import com.avicia.api.data.mapper.FuncionarioMapper;
import com.avicia.api.model.Funcionario;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.FuncionarioRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    @Autowired
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final Random random = new Random();

    public FuncionarioResponse criar(FuncionarioRequest dto) {

        Funcionario funcionario = FuncionarioMapper.toEntity(dto);

        // Recupera o usuário
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        funcionario.setUsuario(usuario);

        // Geração do ID do Funcionario:
        String idUsuarioStr = String.valueOf(usuario.getIdUsuario());

        // Pega os 3 primeiros dígitos
        String prefixo = idUsuarioStr.substring(0, 3);

        // Número aleatório de 3 dígitos
        String numeroAleatorio = String.format("%03d", new Random().nextInt(1000));

        // Concatena e converte para Integer
        Integer idFuncionario = Integer.parseInt(prefixo + numeroAleatorio);

        // Garante unicidade
        while (funcionarioRepository.existsById(idFuncionario)) {
            numeroAleatorio = String.format("%03d", new Random().nextInt(1000));
            idFuncionario = Integer.parseInt(prefixo + numeroAleatorio);
        }
		
		funcionario.setIdFuncionario(idFuncionario);
        Funcionario salvo = funcionarioRepository.save(funcionario);
		
        return FuncionarioMapper.toResponseDTO(salvo);
    }

    public List<FuncionarioResponse> listarTodos() {

        return funcionarioRepository.findAll()
                .stream()
                .map(FuncionarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<FuncionarioResponse> buscarPorId(Integer idFuncionario) {

        return funcionarioRepository.findById(idFuncionario)
                .map(FuncionarioMapper::toResponseDTO);
    }

    public Optional<FuncionarioResponse> buscarPorMatricula(String matricula) {

        return funcionarioRepository.findByMatricula(matricula)
                .map(FuncionarioMapper::toResponseDTO);
    }

    public Optional<FuncionarioResponse> atualizar(String matricula, FuncionarioRequest dto) {

        return funcionarioRepository.findByMatricula(matricula).map(funcionario -> {
            funcionario.setCargo(dto.getCargo());
            funcionario.setSetor(dto.getSetor());
            funcionario.setMatricula(dto.getMatricula());
            funcionario.setObservacoes(dto.getObservacoes());

            // Atualiza o usuário vinculado
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            funcionario.setUsuario(usuario);

            Funcionario atualizado = funcionarioRepository.save(funcionario);
            return FuncionarioMapper.toResponseDTO(atualizado);
        });
    }

    public boolean deletar(String matricula) {
        
        return funcionarioRepository.findByMatricula(matricula).map(funcionario -> {
            funcionarioRepository.delete(funcionario);
            return true;
        }).orElse(false);
    }

}
