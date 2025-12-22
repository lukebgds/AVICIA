package com.avicia.api.features.sistema.auth.login.response;

import java.time.Instant;

public record LoginResponse
(
    String accessToken, 
    Instant expiresIn
) {

}
