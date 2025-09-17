package com.avicia.api.data.dto.response;

import java.time.Instant;

public record LoginResponse
(
    String accessToken, 
    Instant expiresIn
) {

}
