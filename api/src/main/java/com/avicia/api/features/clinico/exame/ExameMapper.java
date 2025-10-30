package com.avicia.api.features.clinico.exame;

import com.avicia.api.features.clinico.exame.request.ExameRequest;
import com.avicia.api.features.clinico.exame.response.ExameResponse;

public class ExameMapper {

    public static ExameResponse toResponseDTO(Exame exame) {

        if (exame == null) return null;

        ExameResponse dto = new ExameResponse();

        dto.setIdExame(exame.getIdExame());
        dto.setNome(exame.getNome());
        dto.setDescricao(exame.getDescricao());
        dto.setTipo(exame.getTipo());
        dto.setAtivo(exame.getAtivo());

        return dto;
    }

    public static Exame toEntity(ExameRequest dto) {
        
        if (dto == null) return null;

        Exame exame = new Exame();
        
        exame.setNome(dto.getNome());
        exame.setDescricao(dto.getDescricao());
        exame.setTipo(dto.getTipo());
        exame.setAtivo(dto.getAtivo());

        return exame;
    }

}
