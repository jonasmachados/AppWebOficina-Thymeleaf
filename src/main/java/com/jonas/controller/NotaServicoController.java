
package com.jonas.controller;

import com.jonas.model.domain.NotaServico;
import com.jonas.model.service.NotaServicoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Jonas, created 13/05/2021
 */

@Controller
@RequestMapping(value = "/")
public class NotaServicoController {
    
    @Autowired
    private NotaServicoService service;
    
    @RequestMapping(value = "/notasServico")
    public String findAllNotasServico(Model model) {
        List<NotaServico> list = service.findAllNotasServico();
        model.addAttribute("notasServico", list);
        return "notaServico/list-notas";
    }
}
