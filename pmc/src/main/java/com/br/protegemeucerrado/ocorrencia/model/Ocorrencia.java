package com.br.protegemeucerrado.ocorrencia.model;

import lombok.Data;

@Data
public class Ocorrencia {

    private Integer id;
    private String descricao;
    private String localizacao;
    private String categoria;
    private Boolean anonimo = false;

}