package com.jonas.model.service;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Pecas;
import com.jonas.repository.PecaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jonas, 14/04/2021.
 */
@Service
public class PecaService {

    @Autowired
    private PecaRepository repository;

    public List<Pecas> findAllPecas() {
        List<Pecas> result = (List<Pecas>) repository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Pecas>();
        }
    }

    public Pecas createOrUpdatePecas(Pecas entity) {
        if (entity.getId() == null) {
            entity = repository.save(entity);
            return entity;
        } else {
            Optional<Pecas> pecas = repository.findById(entity.getId());

            if (pecas.isPresent()) {
                Pecas newPeca = pecas.get();
                newPeca.setDescricao(entity.getDescricao());
                newPeca.setDataCompra(entity.getDataCompra());
                newPeca.setPreco(entity.getPreco());

                newPeca = repository.save(newPeca);

                return newPeca;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public Pecas getPecasById(Integer id) throws RecordNotFoundException {
        Optional<Pecas> peca = repository.findById(id);

        if (peca.isPresent()) {
            return peca.get();
        } else {
            throw new RecordNotFoundException("No peca record exist for given id");
        }
    }

    public void deletePecaById(Integer id) throws RecordNotFoundException {
        Optional<Pecas> peca = repository.findById(id);

        if (peca.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No peca record exist for given id");
        }
    }
}
