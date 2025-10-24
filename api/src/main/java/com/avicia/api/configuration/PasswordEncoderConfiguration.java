package com.avicia.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {

    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(
                    16,   // saltLength
                    32,   // hashLength
                    1,    // parallelism
                    65536, // memory (KB)
                    3     // iterations
                );
    }

}


