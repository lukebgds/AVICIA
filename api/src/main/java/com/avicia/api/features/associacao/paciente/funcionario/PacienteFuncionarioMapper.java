package com.avicia.api.features.associacao.paciente.funcionario;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class PacienteFuncionarioMapper {

    public PacienteFuncionarioResponse toDTO(PacienteFuncionario vinculo) {
        return PacienteFuncionarioResponse.builder()
                .idFuncionario(vinculo.getFuncionario().getIdFuncionario())
                .nomeFuncionario(vinculo.getFuncionario().getUsuario().getNome())
                .cargoFuncionario(vinculo.getFuncionario().getCargo())
                .setorFuncionario(vinculo.getFuncionario().getSetor())

                .idPaciente(vinculo.getPaciente().getIdPaciente())
                .nomePaciente(vinculo.getPaciente().getUsuario().getNome())
                .emailPaciente(vinculo.getPaciente().getUsuario().getEmail())

                .dataVinculo(vinculo.getDataVinculo())
                .build();
    }

    public List<PacienteFuncionarioResponse> toDTOList(List<PacienteFuncionario> vinculos) {
        return vinculos.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
