package com.jonas.model.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;
//import javax.validation.constraints.NotEmpty;
//import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Jonas, created 14/04/2021
 */
@Entity
public class Pecas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private Date dataCompra;
    private Double preco;

    //COSNTRUCTOR
    public Pecas() {
    }

    //COSNTRUCTOR
    public Pecas(Integer id, String descricao, Date dataCompra, Double preco) {
        this.id = id;
        this.descricao = descricao;
        this.dataCompra = dataCompra;
        this.preco = preco;
    }

    ///EQUALS AND HASHCODE
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pecas other = (Pecas) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    //GET AND SET
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String Descricao) {
        this.descricao = descricao;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

}
