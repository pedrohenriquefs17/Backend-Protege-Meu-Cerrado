package com.br.protegemeucerrado.ocorrencia.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.protegemeucerrado.ocorrencia.exception.OcorrenciaException;
import com.br.protegemeucerrado.ocorrencia.model.Categoria;
import com.br.protegemeucerrado.ocorrencia.model.Ocorrencia;
import com.br.protegemeucerrado.ocorrencia.model.Status;
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

    @GetMapping("/status")
    public ResponseEntity<List<Status>> listarStatus() {
        return ResponseEntity.status(200).body(ocServ.listarStatus());
    }

    @PostMapping
    public ResponseEntity<String> cadastrarOcorrencia(
            @RequestParam(value = "idUser", required = false) Integer idUser,
            @RequestParam(value = "idCategoria", required = false) Integer idCategoria,
            @RequestParam(value = "descricao", required = true) String descricao,
            @RequestParam(value = "lat", required = true) String lat,
            @RequestParam(value = "lon", required = true) String lon,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "dtNasc", required = false) String dtNasc,
            @RequestParam(value = "dtOcorrencia", required = false) String dtOcorrencia,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "telefone", required = false) String telefone,
            @RequestParam(value = "imagem", required = true) MultipartFile imagem) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Defina o formato desejado
        Ocorrencia oc = new Ocorrencia();
        oc.setIdUser(idUser);
        oc.setIdCategoria(idCategoria);
        oc.setDescricao(descricao);
        oc.setLat(lat);
        oc.setLon(lon);
        oc.setNome(nome);
        oc.setEmail(email);
        oc.setCpf(cpf);
        oc.setTelefone(telefone);

        try {
            if (dtNasc != null && !dtNasc.isEmpty()) {
                Date dateNasc = sdf.parse(dtNasc);
                oc.setDtNasc(new java.sql.Date(dateNasc.getTime()));
            }
    
            if (dtOcorrencia != null && !dtOcorrencia.isEmpty()) {
                Date dateOcorrencia = sdf.parse(dtOcorrencia);
                oc.setDtOcorrencia(new java.sql.Date(dateOcorrencia.getTime()));
            }
    
        } catch (ParseException e) {
            return new ResponseEntity<>("Erro ao converter data: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            ocServ.cadastrarOcorrencia(oc, imagem);
            return new ResponseEntity<>("Ocorrência cadastrada com sucesso!", HttpStatus.CREATED);
        } catch (OcorrenciaException e) {
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Ocorrencia>> listarOcUser(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ocServ.listarOcUser(id));
    }

    @GetMapping("/listastatus/{id}")
    public ResponseEntity<Integer> listarQtdeStatus(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ocServ.listarQtdeStatus(id));
    }
}
