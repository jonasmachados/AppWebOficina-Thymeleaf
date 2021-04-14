
package com.jonas.repository;

import com.jonas.model.domain.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jonas, created 14/04/2021.
 */
@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer>{

    

}
