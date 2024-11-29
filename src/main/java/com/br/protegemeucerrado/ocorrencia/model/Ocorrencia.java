package com.br.protegemeucerrado.ocorrencia.model;

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
    private Integer idUser;

    @Column(name = "id_categoria", nullable = true)
    private Integer id_categoria;

    @Column(name = "descricao", length = 255, nullable = true)
    private String descricao;

    @Column(name = "is_anon", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean is_anon = false;

}