package com.jonas.model.service;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.NotaServico;
import com.jonas.repository.NotaServicoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jonas
 */
@Service
public class NotaServicoService {
    
    
    @Autowired
    private NotaServicoRepository repository;
    
    public List<NotaServico> findAllNotasServico() {
        List<NotaServico> result = (List<NotaServico>) repository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<NotaServico>();
        }
    }
    
    public NotaServico createOrUpdateNFe(NotaServico entity) {
        if (entity.getId() == null) {
            entity = repository.save(entity);
            return entity;
        } else {
            Optional<NotaServico> nfe = repository.findById(entity.getId());

            if (nfe.isPresent()) {
                NotaServico newNFe = nfe.get();
                newNFe.setDescricao(entity.getDescricao());
                newNFe.setPreco(entity.getPreco());
                newNFe.setDataServico(entity.getDataServico());
                newNFe.setKm(entity.getKm());

                newNFe = repository.save(newNFe);

                return newNFe;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public NotaServico getNotaServicoById(Integer id) throws RecordNotFoundException {
        Optional<NotaServico> nfe = repository.findById(id);

        if (nfe.isPresent()) {
            return nfe.get();
        } else {
            throw new RecordNotFoundException("No nfe record exist for given id");
        }
    }
}
