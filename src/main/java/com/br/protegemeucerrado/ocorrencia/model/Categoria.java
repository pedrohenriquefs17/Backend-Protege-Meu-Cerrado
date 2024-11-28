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
@Table(name = "categorias")
public class Categoria {
    
    //Identifica como uma chave primária
    @Id
    //Identifica como auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identifica o nome da coluna do banco
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome_categoria", length = 150, nullable = true) 
    private String nome_categoria;

}
