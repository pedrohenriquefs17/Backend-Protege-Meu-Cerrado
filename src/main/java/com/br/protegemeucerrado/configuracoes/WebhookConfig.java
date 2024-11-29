// package com.example.projeto.configuracoes;

// import java.io.IOException;
// import java.security.InvalidKeyException;
// import java.security.NoSuchAlgorithmException;

// import javax.crypto.Mac;
// import javax.crypto.spec.SecretKeySpec;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class WebhookConfig {

// @Value("${SECRET_KEY}")
// private String SECRET;

// @PostMapping("/webhook")
// public ResponseEntity<String> handleWebhook(@RequestBody String payload,
// @RequestHeader(value = "X-Hub-Signature", required = false) String signature)
// throws InterruptedException, IOException {

// // Validação do segredo
// if (!isValidSignature(signature, payload)) {
// // Retorne uma resposta de erro (401 Unauthorized ou 403 Forbidden)
// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid
// signature");
// }

// // Processar o payload recebido
// executeDeployScript();
// return ResponseEntity.ok("Webhook handled successfully");
// }

// private boolean isValidSignature(String signature, String payload) {
// if (signature == null || !signature.startsWith("sha1=")) {
// return false; // Assinatura inválida
// }

// // Extraindo a assinatura do GitHub
// String githubSignature = signature.substring("sha1=".length());

// // Gerar a assinatura usando HMAC com SHA-1
// String computedSignature = generateHMACSHA1(payload, SECRET);

// return githubSignature.equals(computedSignature);
// }

// private String generateHMACSHA1(String data, String key) {
// try {
// Mac mac = Mac.getInstance("HmacSHA1");
// SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
// mac.init(secretKeySpec);
// byte[] hmac = mac.doFinal(data.getBytes());
// return bytesToHex(hmac);
// } catch (IllegalStateException | InvalidKeyException |
// NoSuchAlgorithmException e) {
// throw new RuntimeException("Error generating HMAC", e);
// }
// }

// private String bytesToHex(byte[] bytes) {
// StringBuilder sb = new StringBuilder();
// for (byte b : bytes) {
// sb.append(String.format("%02x", b));
// }
// return sb.toString();
// }

// private void executeDeployScript() {
// try {
// String scriptPath = "/home/servidor/webhook/deploy.sh";

// ProcessBuilder processBuilder = new ProcessBuilder("sudo", "-u", "servidor",
// "bash", scriptPath);
// processBuilder.inheritIO(); // Opção para redirecionar a saída do processo
// processBuilder.start(); // Inicia o processo sem esperar que ele termine
// } catch (IOException e) {
// throw new RuntimeException("Error executing deploy script", e);
// }
// }
// }
