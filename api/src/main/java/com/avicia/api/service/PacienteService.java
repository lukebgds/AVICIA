package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.object.PacienteDTO;
import com.avicia.api.data.mapper.PacienteMapper;
import com.avicia.api.model.Paciente;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.PacienteRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    private final UsuarioRepository usuarioRepository;

    public List<PacienteDTO> listarTodos() {

        return pacienteRepository.findAll()
                .stream()
                .map(PacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PacienteDTO> buscarPorId(Integer id) {

        return pacienteRepository.findById(id)
                .map(PacienteMapper::toDTO);
    }

    public Optional<PacienteDTO> buscarPorCpf(String cpf) {

        return pacienteRepository.findByUsuario_Cpf(cpf)
                .map(PacienteMapper::toDTO);
    }

    public PacienteDTO criar(PacienteDTO dto) {

        Paciente paciente = PacienteMapper.toEntity(dto);

        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        paciente.setUsuario(usuario);

        return PacienteMapper.toDTO(pacienteRepository.save(paciente));
    }

    public Optional<PacienteDTO> atualizar(String cpf, PacienteDTO dto) {

        return pacienteRepository.findByUsuario_Cpf(cpf)
                .map(existing -> {
                    Paciente paciente = PacienteMapper.toEntity(dto);
                    paciente.setIdPaciente(existing.getIdPaciente());
                    return PacienteMapper.toDTO(pacienteRepository.save(paciente));
                });
    }

    public boolean deletar(String cpf) {

        return pacienteRepository.findByUsuario_Cpf(cpf)
                .map(existing -> {
                    pacienteRepository.delete(existing);
                    return true;
                })
                .orElse(false);
    }



}
