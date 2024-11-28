package com.br.protegemeucerrado.ocorrencia.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.protegemeucerrado.ocorrencia.model.Categoria;

@Repository
public interface CategoriaDAO extends JpaRepository<Categoria, Integer>{
    
}
