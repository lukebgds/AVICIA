package com.avicia.api.features.sistema.systemLog.request;

import java.time.LocalDateTime;

import com.avicia.api.data.enumerate.TipoSystemLog;

import lombok.Data;

@Data
public class SystemLogRequest {

    private Integer idUsuario;
    private TipoSystemLog tipoLog;
    private String acao;
    private LocalDateTime dataHora;
    private String entidadeAfetada;
    private String detalhes;

}
