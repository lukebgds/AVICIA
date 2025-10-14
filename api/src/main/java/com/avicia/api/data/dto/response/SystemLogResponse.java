package com.avicia.api.data.dto.response;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.TipoSystemLog;

import lombok.Data;

@Data
public class SystemLogResponse {

    private Integer idLog;
    private Integer idUsuario;
    private TipoSystemLog tipoLog;
    private String acao;
    private LocalDate dataHora;
    private String entidadeAfetada;
    private String detalhes;

}
