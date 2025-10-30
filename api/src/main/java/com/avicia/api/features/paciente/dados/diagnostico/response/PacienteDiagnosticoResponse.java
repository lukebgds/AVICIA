package com.avicia.api.features.paciente.dados.diagnostico.response;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.StatusDiagnostico;
import com.avicia.api.data.enumerate.TipoDiagnostico;

import lombok.Data;

@Data
public class PacienteDiagnosticoResponse {

    private Integer idDiagnostico;
    private Integer idPaciente;
    private String codigoCidDez;
    private String descricao;
    private LocalDate dataDiagnostico;
    private TipoDiagnostico tipo;
    private StatusDiagnostico status;

}
