package com.br.protegemeucerrado.ocorrencia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.protegemeucerrado.ocorrencia.exception.OcorrenciaException;
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
    public ResponseEntity<String> cadastrarOcorrencia(@RequestBody Ocorrencia oc) {
        try {
            ocServ.cadastrarOcorrencia(oc);
            return new ResponseEntity<>("Ocorrência cadastrada com sucesso!", HttpStatus.CREATED);
        } catch (OcorrenciaException e) {
            // Aqui a exceção será tratada pelo @ControllerAdvice
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> editarOcorrencia(@RequestBody Ocorrencia oc) {
        try {
            ocServ.editarOcorrencia(oc);
            return new ResponseEntity<>("Ocorrência editada com sucesso!", HttpStatus.CREATED);
        } catch (OcorrenciaException e) {
            // Aqui a exceção será tratada pelo @ControllerAdvice
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirOcorrencia(@PathVariable Integer id) {
        try {
            ocServ.excluirOcorrencia(id);
            return new ResponseEntity<>("Ocorrência deletada com sucesso!", HttpStatus.CREATED);
        } catch (OcorrenciaException e) {
            // Aqui a exceção será tratada pelo @ControllerAdvice
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
