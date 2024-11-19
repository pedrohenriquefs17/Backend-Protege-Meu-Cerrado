package com.br.protegemeucerrado.pmc.model;

import javax.swing.text.html.HTMLDocument.BlockElement;

import lombok.Data;

@Data
public class Ocorrencia {

    private Integer id;
    private String descricao;
    private String localizacao;
    private String categoria;
    private Boolean anonimo = false;

    
}