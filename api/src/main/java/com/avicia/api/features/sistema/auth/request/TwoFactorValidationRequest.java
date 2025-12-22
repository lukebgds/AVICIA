package com.avicia.api.features.sistema.auth.request;

import lombok.Data;

@Data
public class TwoFactorValidationRequest {

    private String email;
    private String codigo;

}
