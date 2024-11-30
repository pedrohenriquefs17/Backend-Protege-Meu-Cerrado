package com.br.protegemeucerrado.ocorrencia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.protegemeucerrado.ocorrencia.DAO.CategoriaDAO;
import com.br.protegemeucerrado.ocorrencia.DAO.OcorrenciaDAO;
import com.br.protegemeucerrado.ocorrencia.model.Categoria;
import com.br.protegemeucerrado.ocorrencia.model.Ocorrencia;

@Service
public class OcorrenciaService {

    private OcorrenciaDAO ocDao;
    private CategoriaDAO catDao;

    public OcorrenciaService(OcorrenciaDAO ocDao, CategoriaDAO catDao) {
        this.ocDao = ocDao;
        this.catDao = catDao;
    }

    public Boolean cadastrarOcorrencia(Ocorrencia oc) {
        if (!oc.getDescricao().isEmpty() && !oc.getLat().isEmpty() && !oc.getLon().isEmpty()) {
            ocDao.save(oc);
            return true;
        } else
            return false;
    }

    public Boolean editarOcorrencia(Ocorrencia oc) {
        if (!oc.getDescricao().isEmpty() && !oc.getLat().isEmpty() && !oc.getLon().isEmpty()) {
            ocDao.save(oc);
            return true;
        } else
            return false;
    }

    public Boolean excluirOcorrencia(Integer id) {
        if (!ocDao.findById(id).isEmpty()) {
            ocDao.deleteById(id);
            return true;
        } else
            return false;

    }

    public List<Ocorrencia> listarOcorrencias() {
        List<Ocorrencia> oc = ocDao.findAll();
        return oc;
    }

    public List<Categoria> listarCategorias() {
        List<Categoria> catOc = catDao.findAll();
        return catOc;
    }

}
