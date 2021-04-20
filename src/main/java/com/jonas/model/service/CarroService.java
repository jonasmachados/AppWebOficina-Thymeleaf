package com.jonas.model.service;

import com.jonas.model.domain.Carro;
import com.jonas.repository.CarroRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jonas, 14/04/2021.
 */
@Service
public class CarroService {

    @Autowired
    private CarroRepository repository;

    public List<Carro> findAllCarros() {
        List<Carro> result = (List<Carro>) repository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Carro>();
        }
    }

}
