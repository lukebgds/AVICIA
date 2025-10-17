package com.avicia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avicia.api.data.dto.request.paciente.PacienteRequest;
import com.avicia.api.data.dto.response.paciente.PacienteResponse;
import com.avicia.api.data.mapper.PacienteMapper;
import com.avicia.api.data.model.Paciente;
import com.avicia.api.data.model.Usuario;
import com.avicia.api.repository.PacienteRepository;
import com.avicia.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    @Autowired
    private final PacienteRepository pacienteRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public List<PacienteResponse> listarTodos() {

        return pacienteRepository.findAll()
                .stream()
                .map(PacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<PacienteResponse> buscarPorId(Integer id) {

        return pacienteRepository.findById(id)
                .map(PacienteMapper::toResponseDTO);
    }

    public Optional<PacienteResponse> buscarPorCpf(String cpf) {

        return pacienteRepository.findByUsuario_Cpf(cpf)
                .map(PacienteMapper::toResponseDTO);
    }

    public PacienteResponse criar(PacienteRequest dto) {

        Paciente paciente = PacienteMapper.toEntity(dto);

        // Recupera o usuário
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        paciente.setUsuario(usuario);

        // Geração do ID do paciente:
        String idUsuarioStr = String.valueOf(usuario.getIdUsuario());

        // Pega os 3 primeiros dígitos
        String prefixo = idUsuarioStr.substring(0, 3);

        // Número aleatório de 3 dígitos
        String numeroAleatorio = String.format("%03d", new Random().nextInt(1_000));

        // Concatena e converte para Integer
        Integer idPaciente = Integer.parseInt(prefixo + numeroAleatorio);

        // Garante unicidade
        while (pacienteRepository.existsById(idPaciente)) {
            numeroAleatorio = String.format("%03d", new Random().nextInt(1_000));
            idPaciente = Integer.parseInt(prefixo + numeroAleatorio);
        }

        paciente.setIdPaciente(idPaciente);

        return PacienteMapper.toResponseDTO(pacienteRepository.save(paciente));
    }

    public Optional<PacienteResponse> atualizar(String cpf, PacienteRequest dto) {

        return pacienteRepository.findByUsuario_Cpf(cpf)
                .map(existing -> {
                    Paciente paciente = PacienteMapper.toEntity(dto);
                    paciente.setIdPaciente(existing.getIdPaciente());
                    return PacienteMapper.toResponseDTO(pacienteRepository.save(paciente));
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
