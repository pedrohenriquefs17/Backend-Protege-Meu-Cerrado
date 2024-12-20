package com.br.protegemeucerrado.usuario.mail.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.br.protegemeucerrado.usuario.mail.dtos.EmailDto;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @SuppressWarnings("UseSpecificCatch")
    public void enviaEmail(EmailDto emailDto) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("protegemeucerrado@gmail.com.br");
            helper.setSubject("VERIFICAÇÃO DE EMAIL");
            helper.setTo(emailDto.email());

            String template = carregaTemplateEmail();

            template = template.replace("#{nome}", emailDto.nome());
            template = template.replace("#{codigo}", String.valueOf(emailDto.codigo_verificador()));
            template = template.replace("#{email}", emailDto.email());
            helper.setText(template, true);
            javaMailSender.send(message);
        } catch (Exception exception) {
            System.out.println("Erro ao enviar e-mail: " + exception.getMessage());
        }
    }

    public String carregaTemplateEmail() throws IOException {
        ClassPathResource resource = new ClassPathResource("template-email.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
