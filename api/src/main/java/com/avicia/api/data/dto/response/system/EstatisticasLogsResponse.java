package com.avicia.api.data.dto.response.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EstatisticasLogsResponse {

    private Long totalLogs;
    private Long totalCriacoes;
    private Long totalModificacoes;
    private Long totalExclusoes;
    private Long totalErros;
    private Long totalAvisos;
    private String usuarioMaisAtivo;
    private Integer idUsuarioMaisAtivo;
    private Long acoesUsuarioMaisAtivo;
    private String entidadeMaisAfetada;
    private Long operacoesEntidadeMaisAfetada;
    
}
