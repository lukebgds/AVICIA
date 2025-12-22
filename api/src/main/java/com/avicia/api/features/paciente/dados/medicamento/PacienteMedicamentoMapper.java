package com.avicia.api.features.paciente.dados.medicamento;

import com.avicia.api.features.paciente.dados.medicamento.request.PacienteMedicamentoRequest;
import com.avicia.api.features.paciente.dados.medicamento.response.PacienteMedicamentoResponse;

public class PacienteMedicamentoMapper {

    public static PacienteMedicamentoResponse toResponseDTO(PacienteMedicamento medicamento) {

        if (medicamento == null) return null;

        PacienteMedicamentoResponse dto = new PacienteMedicamentoResponse();

        dto.setIdMedicamento(medicamento.getIdMedicamento());
        dto.setIdPaciente(medicamento.getPaciente() != null ? medicamento.getPaciente().getIdPaciente() : null);
        dto.setMedicamento(medicamento.getMedicamento());
        dto.setDosagem(medicamento.getDosagem());
        dto.setFrequencia(medicamento.getFrequencia());

        return dto;
    }

    public static PacienteMedicamento toEntity(PacienteMedicamentoRequest dto) {
        
        if (dto == null) return null;

        PacienteMedicamento medicamento = new PacienteMedicamento();
        
        medicamento.setMedicamento(dto.getMedicamento());
        medicamento.setDosagem(dto.getDosagem());
        medicamento.setFrequencia(dto.getFrequencia());

        return medicamento;
    }

}
