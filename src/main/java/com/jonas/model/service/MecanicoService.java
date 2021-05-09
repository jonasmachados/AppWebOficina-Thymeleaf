package com.jonas.model.service;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Mecanico;
import com.jonas.repository.MecanicoRepository;
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
public class MecanicoService {

    @Autowired
    private MecanicoRepository repository;

    public List<Mecanico> findAllMecanicos() {
        List<Mecanico> result = (List<Mecanico>) repository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Mecanico>();
        }
    }
//
//    public Carro createOrUpdateCarro(Carro entity) {
//        if (entity.getId() == null) {
//            entity = repository.save(entity);
//            return entity;
//        } else {
//            Optional<Carro> carro = repository.findById(entity.getId());
//
//            if (carro.isPresent()) {
//                Carro newCarro = carro.get();
//                newCarro.setModelo(entity.getModelo());
//                newCarro.setAno(entity.getAno());
//                newCarro.setChassi(entity.getChassi());
//                newCarro.setCombustivel(entity.getCombustivel());
//                newCarro.setCor(entity.getCor());
//                newCarro.setMarca(entity.getMarca());
//                newCarro.setRenavan(entity.getRenavan());
//
//                newCarro = repository.save(newCarro);
//
//                return newCarro;
//            } else {
//                entity = repository.save(entity);
//
//                return entity;
//            }
//        }
//    }
//
//    public Carro getCarroById(Integer id) throws RecordNotFoundException {
//        Optional<Carro> carro = repository.findById(id);
//
//        if (carro.isPresent()) {
//            return carro.get();
//        } else {
//            throw new RecordNotFoundException("No car record exist for given id");
//        }
//    }
//
//    public void deleteCarroById(Integer id) throws RecordNotFoundException {
//        Optional<Carro> category = repository.findById(id);
//
//        if (category.isPresent()) {
//            repository.deleteById(id);
//        } else {
//            throw new RecordNotFoundException("No category record exist for given id");
//        }
//    }

}
