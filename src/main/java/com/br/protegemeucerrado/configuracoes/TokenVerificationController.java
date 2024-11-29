// package com.br.protegemeucerrado.configuracoes;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import com.auth0.jwt.exceptions.JWTVerificationException;
// import com.br.protegemeucerrado.usuario.security.JwtTokenService;

// @RestController
// public class TokenVerificationController {

// private final JwtTokenService jwtTokenService;

// public TokenVerificationController(JwtTokenService jwtTokenService) {
// this.jwtTokenService = jwtTokenService;
// }

// public static class TokenRequest {
// private String token;

// public String getToken() {
// return token;
// }

// public void setToken(String token) {
// this.token = token;
// }
// }

// @PostMapping("/api/token/verify")
// public ResponseEntity<String> verifyToken(@RequestBody TokenRequest
// tokenRequest) {
// String token = tokenRequest.getToken();
// if (token == null || token.isEmpty()) {
// return ResponseEntity.badRequest().body("Token não fornecido.");
// }

// try {
// String username = jwtTokenService.pegarToken(token);
// return ResponseEntity.ok("Token válido para o usuário: " + username);
// } catch (JWTVerificationException e) {
// return ResponseEntity.status(401).body("Token inválido ou expirado: " +
// e.getMessage());
// } catch (Exception e) {
// return ResponseEntity.status(500).body("Erro interno do servidor: " +
// e.getMessage());
// }
// }
// }
