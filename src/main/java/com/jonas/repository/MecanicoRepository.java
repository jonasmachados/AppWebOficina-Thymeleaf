
package com.jonas.repository;

import com.jonas.model.domain.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jonas, created 14/04/2021.
 */
@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Integer>{

    

}
