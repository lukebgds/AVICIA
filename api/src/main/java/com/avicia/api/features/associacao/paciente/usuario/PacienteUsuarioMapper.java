package com.avicia.api.features.associacao.paciente.usuario;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class PacienteUsuarioMapper {

    public PacienteUsuarioResponse toDTO(PacienteUsuario vinculo) {
        return PacienteUsuarioResponse.builder()
                .idUsuario(vinculo.getUsuario().getIdUsuario())
                .nomeUsuario(vinculo.getUsuario().getNome())
                .emailUsuario(vinculo.getUsuario().getEmail())
                .telefoneUsuario(vinculo.getUsuario().getTelefone())

                .idPaciente(vinculo.getPaciente().getIdPaciente())
                .nomePaciente(vinculo.getPaciente().getUsuario().getNome())
                .emailPaciente(vinculo.getPaciente().getUsuario().getEmail())

                .dataVinculo(vinculo.getDataVinculo())
                .build();
    }

    public List<PacienteUsuarioResponse> toDTOList(List<PacienteUsuario> vinculos) {
        return vinculos.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
