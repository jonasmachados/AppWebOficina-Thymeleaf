package com.jonas.model.service;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Carro;
import com.jonas.repository.CarroRepository;
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
public class CarroService {

    @Autowired
    private CarroRepository repository;

    //METHOD TO FIND KEYWORD TO SEARCH
    public Page<Carro> listAll(int pageNumCarro, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumCarro - 1, 4, sort);

        if (keyword != null) {
            return repository.findAll(keyword, pageable);
        }

        return repository.findAll(pageable);
    }

    public List<Carro> findAllCarros() {
        List<Carro> result = (List<Carro>) repository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Carro>();
        }
    }

    public Carro createOrUpdateCarro(Carro entity) {
        if (entity.getId() == null) {
            entity = repository.save(entity);
            return entity;
        } else {
            Optional<Carro> carro = repository.findById(entity.getId());

            if (carro.isPresent()) {
                Carro newCarro = carro.get();
                newCarro.setModelo(entity.getModelo());
                newCarro.setAno(entity.getAno());
                newCarro.setChassi(entity.getChassi());
                newCarro.setCombustivel(entity.getCombustivel());
                newCarro.setCor(entity.getCor());
                newCarro.setMarca(entity.getMarca());
                newCarro.setRenavan(entity.getRenavan());

                newCarro = repository.save(newCarro);

                return newCarro;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public Carro getCarroById(Integer id) throws RecordNotFoundException {
        Optional<Carro> carro = repository.findById(id);

        if (carro.isPresent()) {
            return carro.get();
        } else {
            throw new RecordNotFoundException("No car record exist for given id");
        }
    }

    public void deleteCarroById(Integer id) throws RecordNotFoundException {
        Optional<Carro> category = repository.findById(id);

        if (category.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No category record exist for given id");
        }
    }

}
