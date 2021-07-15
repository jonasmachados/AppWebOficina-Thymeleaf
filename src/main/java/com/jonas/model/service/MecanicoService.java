package com.jonas.model.service;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Mecanico;
import com.jonas.repository.MecanicoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Mecanico createOrUpdateMecanico(Mecanico entity) {
        if (entity.getId() == null) {
            entity = repository.save(entity);
            return entity;
        } else {
            Optional<Mecanico> listMecanico = repository.findById(entity.getId());

            if (listMecanico.isPresent()) {
                Mecanico newMecanico = listMecanico.get();
                newMecanico.setRazaoSocial(entity.getRazaoSocial());
                newMecanico.setCpf_Cnpj(entity.getCpf_Cnpj());
                newMecanico.setCep(entity.getCep());
                newMecanico.setEndereco(entity.getEndereco());
                newMecanico.setNumero(entity.getNumero());
                newMecanico.setBairro(entity.getBairro());
                newMecanico.setCidade(entity.getCidade());
                newMecanico.setTelefone(entity.getTelefone());
                newMecanico.setAdicionais(entity.getAdicionais());
                newMecanico.setDataCriacao(entity.getDataCriacao());

                newMecanico = repository.save(newMecanico);

                return newMecanico;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public Mecanico getMecanicoById(Integer id) throws RecordNotFoundException {
        Optional<Mecanico> listMecanico = repository.findById(id);

        if (listMecanico.isPresent()) {
            return listMecanico.get();
        } else {
            throw new RecordNotFoundException("No mecanico record exist for given id");
        }
    }

    public void deleteMecanicoById(Integer id) throws RecordNotFoundException {
        Optional<Mecanico> category = repository.findById(id);

        if (category.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No mecanico record exist for given id");
        }
    }

    //METHOD TO FIND KEYWORD TO SEARCH
    public Page<Mecanico> listAll(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        
        Pageable pageable = PageRequest.of(pageNum - 1, 11, sort);

        if (keyword != null) {
            return repository.findAll(keyword, pageable);
        }

        return repository.findAll(pageable);
    }
//    public Page<Mecanico> listAll(int pageNum, String sortField, String sortDir) {
//
//        Pageable pageable = PageRequest.of(pageNum - 1, 5,
//                sortDir.equals("asc") ? Sort.by(sortField).ascending()
//                : Sort.by(sortField).descending()
//        );
//
//        return repository.findAll(pageable);
//    }

}
