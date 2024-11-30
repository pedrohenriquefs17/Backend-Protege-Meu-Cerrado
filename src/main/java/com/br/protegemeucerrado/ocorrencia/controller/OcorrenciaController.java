package com.br.protegemeucerrado.ocorrencia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.protegemeucerrado.ocorrencia.model.Categoria;
import com.br.protegemeucerrado.ocorrencia.model.Ocorrencia;
import com.br.protegemeucerrado.ocorrencia.service.OcorrenciaService;

@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocServ;

    @GetMapping
    public ResponseEntity<List<Ocorrencia>> listarOcorrencias() {
        return ResponseEntity.status(200).body(ocServ.listarOcorrencias());
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategorias() {
        return ResponseEntity.status(200).body(ocServ.listarCategorias());
    }

    @PostMapping
    public ResponseEntity<Ocorrencia> cadastrarOcorrencia(@RequestBody Ocorrencia oc) {
        if (ocServ.cadastrarOcorrencia(oc)) {
            return ResponseEntity.status(201).body(oc);
        } else
            return ResponseEntity.status(400).body(oc);
    }

    @PutMapping
    public ResponseEntity<Ocorrencia> editarOcorrencia(@RequestBody Ocorrencia oc) {
        if (ocServ.editarOcorrencia(oc)) {
            return ResponseEntity.status(201).body(oc);
        } else
            return ResponseEntity.status(400).body(oc);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirOcorrencia(@PathVariable Integer id) {
        if(ocServ.excluirOcorrencia(id)){
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(400).build();
    }
}
