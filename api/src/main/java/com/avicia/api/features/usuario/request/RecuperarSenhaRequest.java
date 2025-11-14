package com.avicia.api.features.usuario.request;

import lombok.Data;

@Data
public class RecuperarSenhaRequest {
    private String cpf;
    private String senhaNova;
}
