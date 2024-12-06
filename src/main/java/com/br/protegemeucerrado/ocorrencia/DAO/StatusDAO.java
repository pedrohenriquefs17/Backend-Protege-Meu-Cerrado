package com.br.protegemeucerrado.ocorrencia.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.protegemeucerrado.ocorrencia.model.Status;

@Repository
public interface StatusDAO extends JpaRepository<Status, Integer>{

    
} 
