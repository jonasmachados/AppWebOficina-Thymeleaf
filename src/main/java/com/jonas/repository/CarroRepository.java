package com.jonas.repository;

import com.jonas.model.domain.Carro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jonas, created 14/04/2021.
 */
@Repository
public interface CarroRepository extends JpaRepository<Carro, Integer> {

    @Query("SELECT c FROM Carro c WHERE "
            + "lower(CONCAT(c.id, ' ', c.modelo, ' ', c.marca, ' ', c.ano, ' ', c.chassi, ' ', c.Renavan, ' ', c.cor, ' ', c.combustivel, ' '))"
            + " LIKE %?1%")
    public Page<Carro> findAll(String keyword, Pageable pageable);
}
