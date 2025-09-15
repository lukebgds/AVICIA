package com.avicia.api.data.dto;

import java.time.Instant;

public record LoginResponse
(
    String accessToken, 
    Instant expiresIn
) {

}
