package com.avicia.api.security;

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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

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
                    String recurso = entry.getKey().toUpperCase(); // logs -> LOGS
                    String valor = entry.getValue(); // R, W, RW

                    List<GrantedAuthority> auths = new ArrayList<>();

                    if (valor.contains("R")) {
                        auths.add(new SimpleGrantedAuthority(recurso + "_READ"));
                    }
                    if (valor.contains("W")) {
                        auths.add(new SimpleGrantedAuthority(recurso + "_WRITE"));
                    }

                    return auths.stream();
                })
                .collect(Collectors.toSet());
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInputType'");
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOutputType'");
    }

}
