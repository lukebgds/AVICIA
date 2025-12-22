package com.avicia.api.features.clinico.consulta.prescricao.item;

import com.avicia.api.features.clinico.consulta.prescricao.item.request.PrescricaoItemRequest;
import com.avicia.api.features.clinico.consulta.prescricao.item.response.PrescricaoItemResponse;

public class PrescricaoItemMapper {

    public static PrescricaoItemResponse toResponseDTO(PrescricaoItem item) {

        if (item == null) return null;

        PrescricaoItemResponse dto = new PrescricaoItemResponse();

        dto.setIdItem(item.getIdItem());
        dto.setIdPrescricao(item.getConsultaPrescricao() != null 
            ? item.getConsultaPrescricao().getIdPrescricao() : null);
        dto.setMedicamento(item.getMedicamento());
        dto.setDosagem(item.getDosagem());
        dto.setFrequencia(item.getFrequencia());
        dto.setDuracao(item.getDuracao());

        return dto;
    }

    public static PrescricaoItem toEntity(PrescricaoItemRequest dto) {
        
        if (dto == null) return null;

        PrescricaoItem item = new PrescricaoItem();
        
        item.setMedicamento(dto.getMedicamento());
        item.setDosagem(dto.getDosagem());
        item.setFrequencia(dto.getFrequencia());
        item.setDuracao(dto.getDuracao());

        return item;
    }


}
