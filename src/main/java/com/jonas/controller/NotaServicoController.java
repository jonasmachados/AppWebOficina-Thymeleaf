
package com.jonas.controller;

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
    
//    @Autowired
//    private NotaServicoService service;
    
    @RequestMapping(value = "/serviceNotes")
    public String findAllCategory(Model model) {
//        List<NotaServico> list = service.findAllNotasServico();
        model.addAttribute("serviceNotes");
        return "notaServico/list-notas";
    }
}
