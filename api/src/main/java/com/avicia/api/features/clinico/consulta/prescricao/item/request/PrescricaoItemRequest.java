package com.avicia.api.features.clinico.consulta.prescricao.item.request;

import lombok.Data;

@Data
public class PrescricaoItemRequest {

    private Integer idPrescricao;
    private String medicamento;
    private String dosagem;
    private String frequencia;
    private String duracao;

}
