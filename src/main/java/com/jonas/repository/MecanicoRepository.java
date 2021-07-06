package com.jonas.repository;

import com.jonas.model.domain.Mecanico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jonas, created 14/04/2021.
 */
@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Integer> {

    @Query("SELECT m FROM Mecanico m WHERE CONCAT(m.razaoSocial, ' ', m.cpf_Cnpj, ' ', m.cep, ' ', m.cep, m.endereco) LIKE %?1%")
    public List<Mecanico> search(String keyword);

}
