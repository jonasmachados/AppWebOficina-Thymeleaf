package com.jonas.controller;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Pecas;
import com.jonas.model.service.PecaService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Jonas, created 14/04/2021.
 */
@Controller
@RequestMapping(value = "/")
public class PecaController {

    @Autowired
    private PecaService service;

    @RequestMapping(value = "/pecas")
    public String findAllPecas(Model model) {
        List<Pecas> list = service.findAllPecas();
        model.addAttribute("pecas", list);
        return "peca/list-peca";
    }
//
//    @RequestMapping(path = {"/editMecanico", "/editMecanico{id}"})
//    public String editMecanicoById(Model model, @PathVariable("id") Optional<Integer> id)
//            throws RecordNotFoundException {
//        if (id.isPresent()) {
//            Mecanico entity = service.getMecanicoById(id.get());
//            model.addAttribute("mecanico", entity);
//        } else {
//            model.addAttribute("mecanico", new Mecanico());
//        }
//        return "mecanico/add-edit-mecanico";
//    }
//
//    @RequestMapping(path = "/createMecanico", method = RequestMethod.POST)
//    public String createOrUpdateMecanico(Mecanico mecanico) {
//        service.createOrUpdateMecanico(mecanico);
//        return "redirect:/mecanicos"; //REDIRECT: back to previous HTML.
//    }
//
//    @RequestMapping(path = "/deleteMecanico/{id}")
//   public String deleteCarroById(Model model, @PathVariable("id") Integer id)
//           throws RecordNotFoundException {
//        service.deleteMecanicoById(id);
//       return "redirect:/mecanicos";
//   }
}
