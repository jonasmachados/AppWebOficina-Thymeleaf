package com.jonas.model.service;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Carro;
import com.jonas.model.domain.Pecas;
import com.jonas.repository.PecaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jonas, 14/04/2021.
 */
@Service
public class PecaService {

    @Autowired
    private PecaRepository repository;

    public Page<Pecas> listAll(int pageNumPeca, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumPeca - 1, 4, sort);

        if (keyword != null) {
            return repository.findAll(keyword, pageable);
        }

        return repository.findAll(pageable);
    }

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
