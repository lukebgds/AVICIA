package com.avicia.api.features.sistema.token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.core.convert.converter.Converter;

public class CustomJwtConverter implements Converter<Jwt, AbstractAuthenticationToken>{

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Map<String, String> permissoes = jwt.getClaim("permissoes");

        if (permissoes == null) {
            return Collections.emptyList();
        }

        return permissoes.entrySet().stream()
                .flatMap(entry -> {
                    String recurso = entry.getKey().toUpperCase();
                    String valor = entry.getValue(); 

                    List<GrantedAuthority> auths = new ArrayList<>();

                    if (valor.contains("C")) {
                        auths.add(new SimpleGrantedAuthority(recurso + "_CREATE"));
                    }
                    if (valor.contains("R")) {
                        auths.add(new SimpleGrantedAuthority(recurso + "_READ"));
                    }
                    if (valor.contains("U")) {
                        auths.add(new SimpleGrantedAuthority(recurso + "_UPDATE"));
                    }
                    if (valor.contains("D")) {
                        auths.add(new SimpleGrantedAuthority(recurso + "_DELETE"));
                    }

                    return auths.stream();
                })
                .collect(Collectors.toSet());
    }

}
