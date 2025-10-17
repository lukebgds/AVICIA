package com.avicia.api.data.dto.response.login;

import java.time.Instant;

public record LoginResponse
(
    String accessToken, 
    Instant expiresIn
) {

}
