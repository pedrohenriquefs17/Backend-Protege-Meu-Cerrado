package com.br.protegemeucerrado.ocorrencia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.protegemeucerrado.ocorrencia.DAO.CategoriaDAO;
import com.br.protegemeucerrado.ocorrencia.DAO.OcorrenciaDAO;
import com.br.protegemeucerrado.ocorrencia.exception.OcorrenciaException;
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

    public Boolean cadastrarOcorrencia(Ocorrencia oc) throws OcorrenciaException {
        if (oc.getDescricao().isEmpty() || oc.getLat().isEmpty() || oc.getLon().isEmpty()) {
            throw new OcorrenciaException("Descrição, Latitude ou Longitude não podem estar vazios.");
        }

        if (oc.getId_user() != null) {
            oc.setNome(null);
            oc.setCpf(null);
            oc.setDt_nasc(null);
            oc.setTelefone(null);
            oc.setEmail(null);
            ocDao.save(oc);
            return true;
        } else {
            if (oc.getNome().isEmpty() || oc.getCpf().isEmpty() || oc.getTelefone().isEmpty()
                    || oc.getEmail().isEmpty()) {
                throw new OcorrenciaException(
                        "Nome, CPF, Telefone ou Email não podem estar vazios para usuários não associados.");
            }
            ocDao.save(oc);
            return true;
        }
    }

    public Boolean editarOcorrencia(Ocorrencia oc) {
        if (ocDao.findById(oc.getId()).isEmpty()) {
            throw new OcorrenciaException("Ocorrência não encontrada.");
        }
        if (oc.getDescricao().isEmpty() || oc.getLat().isEmpty() || oc.getLon().isEmpty()) {
            throw new OcorrenciaException("Descrição, Latitude ou Longitude não podem estar vazios.");
        }

        if (oc.getId_user() != null) {
            oc.setNome(null);
            oc.setCpf(null);
            oc.setDt_nasc(null);
            oc.setTelefone(null);
            oc.setEmail(null);
            ocDao.save(oc);
            return true;
        } else {
            if (oc.getNome().isEmpty() || oc.getCpf().isEmpty() || oc.getTelefone().isEmpty()
                    || oc.getEmail().isEmpty()) {
                throw new OcorrenciaException(
                        "Nome, CPF, Telefone ou Email não podem estar vazios para usuários não associados.");
            }
            ocDao.save(oc);
            return true;
        }
    }

    public Boolean excluirOcorrencia(Integer id) {
        if (ocDao.findById(id).isEmpty()) {
            throw new OcorrenciaException("Ocorrência não encontrada.");
        }
        ocDao.deleteById(id);
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
