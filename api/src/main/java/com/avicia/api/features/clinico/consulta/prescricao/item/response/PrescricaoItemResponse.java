package com.avicia.api.features.clinico.consulta.prescricao.item.response;

import lombok.Data;

@Data
public class PrescricaoItemResponse {

    private Integer idItem;
    private Integer idPrescricao;
    private String medicamento;
    private String dosagem;
    private String frequencia;
    private String duracao;

}
