package com.jonas.model.service;

import com.jonas.model.domain.NotaServico;
import com.jonas.repository.NotaServicoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jonas
 */
@Service
public class NotaServicoService {
    
    
    @Autowired
    private NotaServicoRepository repository;
    
    public List<NotaServico> findAllNotasServico() {
        List<NotaServico> result = (List<NotaServico>) repository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<NotaServico>();
        }
    }
}
