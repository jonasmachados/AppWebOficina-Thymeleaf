
package com.jonas.repository;

import com.jonas.model.domain.Pecas;
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
public interface PecaRepository extends JpaRepository<Pecas, Integer>{

    @Query("SELECT p FROM Pecas p WHERE "
            + "lower(CONCAT(p.id, ' ', p.descricao, ' ', p.dataCompra, ' ', p.preco, ' '))"
            + " LIKE %?1%")
    public Page<Pecas> findAll(String keyword, Pageable pageable);
}
