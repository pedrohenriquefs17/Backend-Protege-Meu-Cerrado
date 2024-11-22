package com.br.protegemeucerrado.ocorrencia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.protegemeucerrado.ocorrencia.DAO.OcorrenciaDAO;
import com.br.protegemeucerrado.ocorrencia.model.Ocorrencia;

@Service
public class OcorrenciaService {
    
    private OcorrenciaDAO ocDao;
    public OcorrenciaService(OcorrenciaDAO ocDao){
        this.ocDao = ocDao;
    }

    public Ocorrencia cadastrarOcorrencia(Ocorrencia oc){
        ocDao.save(oc);
        return oc;
    }

    public Ocorrencia editarOcorrencia (Ocorrencia oc){
        ocDao.save(oc);
        return oc;
    }

    public Boolean excluirOcorrencia(Integer id){
        ocDao.deleteById(id);
        return true;
    }

    public List<Ocorrencia> listarOcorrencias(){
        List<Ocorrencia> oc = ocDao.findAll();
        return oc;
    }
}
