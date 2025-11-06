package com.avicia.api.features.associacao.paciente.profissional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class PacienteProfissionalSaudeMapper {


    public PacienteProfissionalSaudeResponse toDTO(PacienteProfissionalSaude entity) {
        if (entity == null) return null;

        return PacienteProfissionalSaudeResponse.builder()
                .idProfissional(entity.getProfissionalSaude().getIdProfissional())
                .nomeProfissional(entity.getProfissionalSaude().getUsuario().getNome())
                .especialidade(entity.getProfissionalSaude().getEspecialidade())
                .unidade(entity.getProfissionalSaude().getUnidade())

                .idPaciente(entity.getPaciente().getIdPaciente())
                .nomePaciente(entity.getPaciente().getUsuario().getNome())

                .dataVinculo(entity.getDataVinculo())
                .build();
    }

    public List<PacienteProfissionalSaudeResponse> toDTOList(List<PacienteProfissionalSaude> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
