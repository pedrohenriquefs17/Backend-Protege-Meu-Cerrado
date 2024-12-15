package com.br.protegemeucerrado.ocorrencia.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.br.protegemeucerrado.ocorrencia.DAO.CategoriaDAO;
import com.br.protegemeucerrado.ocorrencia.DAO.OcorrenciaDAO;
import com.br.protegemeucerrado.ocorrencia.DAO.StatusDAO;
import com.br.protegemeucerrado.ocorrencia.exception.OcorrenciaException;
import com.br.protegemeucerrado.ocorrencia.model.Categoria;
import com.br.protegemeucerrado.ocorrencia.model.Ocorrencia;
import com.br.protegemeucerrado.ocorrencia.model.Status;
import com.br.protegemeucerrado.ocorrencia.util.UploadUtil;

@Service
public class OcorrenciaService {

    private OcorrenciaDAO ocDao;
    private CategoriaDAO catDao;
    private StatusDAO staDao;

    public OcorrenciaService(OcorrenciaDAO ocDao, CategoriaDAO catDao, StatusDAO staDao) {
        this.ocDao = ocDao;
        this.catDao = catDao;
        this.staDao = staDao;
    }

    public Boolean cadastrarOcorrencia(Ocorrencia oc, MultipartFile imagem) throws OcorrenciaException {

        oc.setIdStatus(1);

        try {
            if(UploadUtil.uploadImagem(imagem, oc)){
                oc.setImagem(imagem.getOriginalFilename());
            }
        } catch (Exception e) {
            throw new OcorrenciaException("Erro ao fazer upload de imagem.");
        }

        if (oc.getDescricao().isEmpty() || oc.getLat().isEmpty() || oc.getLon().isEmpty()) {
            throw new OcorrenciaException("Descrição, Latitude ou Longitude não podem estar vazios.");
        }

        if (oc.getIdUser() != null) {
            oc.setNome(null);
            oc.setCpf(null);
            oc.setDtNasc(null);
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

        if (oc.getIdUser() != null) {
            oc.setNome(null);
            oc.setCpf(null);
            oc.setDtNasc(null);
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

    public List<Ocorrencia> listarOcUser(Integer id) {
        List<Ocorrencia> oc = (List<Ocorrencia>) ocDao.findByIdUser(id);
        return oc;
    }

    public Integer listarQtdeStatus(Integer id) {
        Integer oc = ocDao.countByIdStatus(id);
        return oc;
    }

    public List<Categoria> listarCategorias() {
        List<Categoria> catOc = catDao.findAll();
        return catOc;
    }

    public List<Status> listarStatus() {
        List<Status> staOc = staDao.findAll();
        return staOc;
    }

}

