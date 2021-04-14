
package com.jonas.repository;

import com.jonas.model.domain.Pecas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jonas, created 14/04/2021.
 */
@Repository
public interface PecaRepository extends JpaRepository<Pecas, Integer>{

}
