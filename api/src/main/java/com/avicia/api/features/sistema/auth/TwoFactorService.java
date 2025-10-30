package com.avicia.api.features.sistema.auth;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TwoFactorService {

    private static final int EXPIRACAO_SEGUNDOS = 300; // 5 minutos
    private static final int MAX_TENTATIVAS = 3;

    private record TwoFactorEntry(String codigo, Instant expiracao, int tentativas) {}

    private final Map<String, TwoFactorEntry> codigosAtivos = new ConcurrentHashMap<>();

        public String gerarCodigo(String email) {
        String codigo = String.format("%06d", new Random().nextInt(999999));
        Instant expiracao = Instant.now().plusSeconds(EXPIRACAO_SEGUNDOS);
        codigosAtivos.put(email, new TwoFactorEntry(codigo, expiracao, 0));
        return codigo;
        }

        public boolean validarCodigo(String email, String codigoDigitado) {
        TwoFactorEntry entry = codigosAtivos.get(email);

        if (entry == null)
            return false;

        // Expirado?
        if (Instant.now().isAfter(entry.expiracao)) {
            codigosAtivos.remove(email);
            return false;
        }

        // Código errado?
        if (!entry.codigo.equals(codigoDigitado)) {
            int novasTentativas = entry.tentativas + 1;
            if (novasTentativas >= MAX_TENTATIVAS)
                codigosAtivos.remove(email);
            else
                codigosAtivos.put(email, new TwoFactorEntry(entry.codigo, entry.expiracao, novasTentativas));
            return false;
        }

        // Código correto → remove para evitar reutilização
        codigosAtivos.remove(email);
        return true;
    }

}
