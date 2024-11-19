package com.br.protegemeucerrado.pmc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.protegemeucerrado.pmc.model.Ocorrencia;

@RestController
@CrossOrigin("*")
@RequestMapping("/ocorrencias")
public class OcorrenciaController {


    @GetMapping
    public ResponseEntity<?> listarOcorrencias(){
        return ResponseEntity.status(200).build();
    }
}