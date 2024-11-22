package com.br.protegemeucerrado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProtegeMeuCerrado {

    public static void main(String[] args) {
        SpringApplication.run(ProtegeMeuCerrado.class, args);
    }
}
