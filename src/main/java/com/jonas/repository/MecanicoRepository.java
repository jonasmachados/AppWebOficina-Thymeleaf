package com.jonas.repository;

import com.jonas.model.domain.Mecanico;
//import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jonas, created 14/04/2021.
 */
@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Integer> {

    @Query("SELECT m FROM Mecanico m WHERE " //            + "lower(CONCAT(m.razaoSocial, ' ', m.cpf_Cnpj, ' ', m.cep, ' ', m.endereco, ' ', m.numero, ' ', m.bairro, ' ', m.cidade, ' ', m.telefone, ' ',  m.adicionais, ' ', m.dataCriacao, ' '))"
            + "lower(CONCAT(m.id, ' ', m.razaoSocial, ' ', m.cpf_Cnpj, ' ', m.cep, ' ', m.endereco, ' '))"
            + " LIKE %?1%")
    public Page<Mecanico> findAll(String keyword, Pageable pageable);

}
