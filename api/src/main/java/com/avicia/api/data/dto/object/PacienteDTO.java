package com.avicia.api.data.dto.object;

import java.time.LocalDate;

import com.avicia.api.data.enumerate.PreferenciaContato;
import com.avicia.api.data.enumerate.Sexo;

import lombok.Data;

@Data
public class PacienteDTO {

    private Integer idPaciente;
    private UsuarioDTO usuario;
    private LocalDate dataNascimento;
    private Sexo sexo;
    private String estadoCivil;
    private String profissao;
    private String endereco;
    private String telefone;
    private PreferenciaContato preferenciaContato;

}
