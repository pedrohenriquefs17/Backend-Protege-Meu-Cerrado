package com.br.protegemeucerrado.ocorrencia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Ocorrencia {

    //Identifica como uma chave primária
    @Id
    //Identifica como auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identifica o nome da coluna do banco
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DESCRICAO", length = 255, nullable = true)
    private String descricao;

    @Column(name = "LOCALIZACAO", length = 60, nullable = true)
    private String localizacao;

    @Column(name = "CATEGORIA", columnDefinition = "INT", nullable = true)
    private String categoria;

    @Column(name = "ANONIMO", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean anonimo = false;

}