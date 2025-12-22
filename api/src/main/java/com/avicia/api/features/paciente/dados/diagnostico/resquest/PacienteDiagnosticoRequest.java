package com.avicia.api.features.paciente.dados.diagnostico.resquest;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.StatusDiagnostico;
import com.avicia.api.data.enumerate.TipoDiagnostico;

import lombok.Data;

@Data
public class PacienteDiagnosticoRequest {

    private Integer idPaciente;
    private String codigoCidDez;
    private String descricao;
    private LocalDate dataDiagnostico;
    private TipoDiagnostico tipo;
    private StatusDiagnostico status;

}
