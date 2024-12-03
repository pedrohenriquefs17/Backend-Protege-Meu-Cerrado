package com.br.protegemeucerrado.ocorrencia.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ocorrencias")
public class Ocorrencia {

    // Identifica como uma chave primária
    @Id
    // Identifica como auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Identifica o nome da coluna do banco
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_user", nullable = true)
    private Integer id_user;

    @Column(name = "id_categoria", nullable = true)
    private Integer id_categoria;

    @Column(name = "nome", length = 255, nullable = true)
    private String nome;

    @Column(name = "email", length = 255, nullable = true)
    private String email;

    @Column(name = "cpf", length = 15, nullable = true)
    private String cpf;

    @Column(name = "telefone", length = 15, nullable = true)
    private String telefone;

    @Column(name = "dt_nasc", columnDefinition = "DATE", nullable = true)
    private Date dt_nasc;

    @Column(name = "descricao", length = 255, nullable = true)
    private String descricao;

    @Column(name = "is_anon", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean is_anon = false;

    @Column(name = "dt_ocorrencia", columnDefinition = "DATE", nullable = true)
    private Date dt_ocorrencia;

    @Column(name = "lat", length = 150, nullable = true)
    private String lat;

    @Column(name = "lon", length = 150, nullable = true)
    private String lon;
}