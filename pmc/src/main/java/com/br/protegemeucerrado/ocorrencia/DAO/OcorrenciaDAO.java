package com.br.protegemeucerrado.ocorrencia.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.protegemeucerrado.ocorrencia.model.Ocorrencia;

@Repository
public interface OcorrenciaDAO extends JpaRepository<Ocorrencia, Integer>{
    
}
