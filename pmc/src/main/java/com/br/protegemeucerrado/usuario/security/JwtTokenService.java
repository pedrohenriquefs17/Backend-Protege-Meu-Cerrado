package com.br.protegemeucerrado.usuario.security;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.br.protegemeucerrado.usuario.model.ModelUserDetailsImpl;

@Service
public class JwtTokenService {

    @Value("${token.jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expirationHours}")
    private int expirationHours;

    public String generateToken(ModelUserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            // Obter as roles como uma lista de Strings
            List<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return JWT.create()
                    .withIssuedAt(dataCriacao())
                    .withExpiresAt(dataExpiracao())// 1 dia de expiração
                    .withSubject(user.getModelUser().getId().toString())// nome de usuário (subject)
                    .withClaim("roles", roles)// Adicionar roles ao payload
                    .sign(algorithm);// assinar o token
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token: " + exception.getMessage(), exception);
        }
    }

    public String pegarToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token não pode ser nulo ou vazio!");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return decodedJWT.getSubject(); // Extraia o sujeito (username ou id)
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token invalido ou expirado!", e);
        }
    }

    private Instant dataExpiracao() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
                .plusHours(expirationHours).toInstant();
    }

    private Instant dataCriacao() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }
}
