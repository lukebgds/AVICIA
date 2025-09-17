package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.object.FuncionarioDTO;
import com.avicia.api.data.mapper.FuncionarioMapper;
import com.avicia.api.model.Funcionario;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.FuncionarioRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    private final UsuarioRepository usuarioRepository;

    public FuncionarioDTO criar(FuncionarioDTO dto) {

        Funcionario funcionario = FuncionarioMapper.toEntity(dto);

        // Garante que o usuário existe antes de salvar
        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        funcionario.setUsuario(usuario);

        Funcionario salvo = funcionarioRepository.save(funcionario);

        return FuncionarioMapper.toDTO(salvo);
    }

    public List<FuncionarioDTO> listarTodos() {

        return funcionarioRepository.findAll()
                .stream()
                .map(FuncionarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<FuncionarioDTO> buscarPorId(Integer idAdministrativo) {

        return funcionarioRepository.findById(idAdministrativo)
                .map(FuncionarioMapper::toDTO);
    }

    public Optional<FuncionarioDTO> buscarPorMatricula(String matricula) {

        return funcionarioRepository.findByMatricula(matricula)
                .map(FuncionarioMapper::toDTO);
    }

    public Optional<FuncionarioDTO> atualizar(Integer idAdministrativo, FuncionarioDTO dto) {

        return funcionarioRepository.findById(idAdministrativo).map(funcionario -> {
            funcionario.setCargo(dto.getCargo());
            funcionario.setSetor(dto.getSetor());
            funcionario.setMatricula(dto.getMatricula());
            funcionario.setObservacoes(dto.getObservacoes());

            // Atualiza o usuário vinculado
            Usuario usuario = usuarioRepository.findById(dto.getUsuario().getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            funcionario.setUsuario(usuario);

            Funcionario atualizado = funcionarioRepository.save(funcionario);

            return FuncionarioMapper.toDTO(atualizado);
        });        
    }

    public boolean deletar(Integer idAdministrativo) {
        
        return funcionarioRepository.findById(idAdministrativo).map(funcionario -> {
            funcionarioRepository.delete(funcionario);
            return true;
        }).orElse(false);
    }

}
