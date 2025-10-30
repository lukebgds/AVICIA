package com.avicia.api.features.sistema.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.features.sistema.auth.request.TwoFactorRequest;
import com.avicia.api.features.sistema.auth.request.TwoFactorValidationRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sistema/auth/2fa")
@RequiredArgsConstructor
public class AuthController {

    private final TwoFactorService twoFactorService;
    private final EmailService emailService;

    public ResponseEntity<?> enviarCodigo(@RequestBody TwoFactorRequest dto) {
        String codigo = twoFactorService.gerarCodigo(dto.getEmail());
        String corpo = String.format("""
                Olá!

                Seu código de verificação é: %s

                Ele expira em 5 minutos.

                Se você não solicitou este código, ignore este e-mail.
                """, codigo);
        emailService.enviarEmail(dto.getEmail(), "Código de Verificação - AVICIA", corpo);
        return ResponseEntity.ok("Código enviado com sucesso!");
    }

    @PostMapping("/validar")
    public ResponseEntity<?> validarCodigo(@RequestBody TwoFactorValidationRequest dto) {
        boolean valido = twoFactorService.validarCodigo(dto.getEmail(), dto.getCodigo());
        if (!valido) {
            return ResponseEntity.badRequest().body("Código inválido ou expirado.");
        }
        return ResponseEntity.ok("Código validado com sucesso!");
    }

}
