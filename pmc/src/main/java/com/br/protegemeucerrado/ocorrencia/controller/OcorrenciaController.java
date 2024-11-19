package com.br.protegemeucerrado.ocorrencia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    @GetMapping
    public ResponseEntity<?> listarOcorrencias() {
        return ResponseEntity.status(200).build();
    }
}
