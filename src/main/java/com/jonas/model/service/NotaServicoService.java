package com.jonas.model.service;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.NotaServico;
import com.jonas.repository.NotaServicoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonas.model.domain.Mecanico;
import com.jonas.repository.MecanicoRepository;

/**
 *
 * @author Jonas
 */
@Service
public class NotaServicoService {

    @Autowired
    private NotaServicoRepository repository;

    @Autowired
    private MecanicoRepository mecanicoRepository;

    public List<NotaServico> findAllNotasServico() {
        List<NotaServico> result = (List<NotaServico>) repository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<NotaServico>();
        }
    }

    public List<Mecanico> findAllMecanico() {
        List<Mecanico> result = (List<Mecanico>) mecanicoRepository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Mecanico>();
        }
    }

    public NotaServico getNotaServicoById(Integer id) throws RecordNotFoundException {
        Optional<NotaServico> notaServico = repository.findById(id);

        if (notaServico.isPresent()) {
            return notaServico.get();
        } else {
            throw new RecordNotFoundException("No NFe record exist for given id");
        }
    }

    public NotaServico createOrUpdateNotaServico(NotaServico entity) {
        if (entity.getId() == null) {
            entity = repository.save(entity);

            return entity;
        } else {
            Optional<NotaServico> notaServico = repository.findById(entity.getId());

            if (notaServico.isPresent()) {
                NotaServico newNotaServico = notaServico.get();
                newNotaServico.setDescricao(entity.getDescricao());
                newNotaServico.setPreco(entity.getPreco());
                newNotaServico.setDataServico(entity.getDataServico());
                newNotaServico.setKm(entity.getKm());
                newNotaServico.setMecanico(entity.getMecanico());

                newNotaServico = repository.save(newNotaServico);

                return newNotaServico;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public void deleteNFeById(Integer id) throws RecordNotFoundException {
        Optional<NotaServico> nfe = repository.findById(id);

        if (nfe.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No nfe record exist for given id");
        }
    }
}
