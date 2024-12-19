package com.br.protegemeucerrado.configuracoes;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.br.protegemeucerrado.usuario.security.JwtTokenService;

@RestController
public class TokenVerificationController {

    private final JwtTokenService jwtTokenService;

    public TokenVerificationController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    public static class TokenRequest {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    @PostMapping("/api/token/verify")
    public ResponseEntity<String> verifyToken(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Token não fornecido.");
        }

        try {
            String username = jwtTokenService.pegarToken(token);
            return ResponseEntity.ok("Token válido para o usuário: " + username);
        } catch (JWTVerificationException e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado: " +
                    e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno do servidor: " +
                    e.getMessage());
        }
    }

    private final String secret = "My$uper$ecretK3y!2024";

    @PostMapping("/token/role")
    public ResponseEntity<String> getTokenRole(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Token não fornecido.");
        }

        try {
            // Decodifica o token
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);

            // Extrai o array de roles
            List<String> roles = jwt.getClaim("roles").asList(String.class);

            if (roles != null && !roles.isEmpty()) {
                return ResponseEntity.ok(roles.get(0)); // Retorna a primeira role
            } else {
                return ResponseEntity.status(404).body("Nenhuma role encontrada no token.");
            }
        } catch (JWTVerificationException e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno do servidor: " + e.getMessage());
        }
    }
}
