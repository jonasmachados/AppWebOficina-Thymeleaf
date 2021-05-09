package com.jonas.controller;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Mecanico;
import com.jonas.model.service.MecanicoService;
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
public class MecanicoController {

    @Autowired
    private MecanicoService service;

    @RequestMapping(value = "/mecanicos")
    public String findAllMecanicos(Model model) {
        List<Mecanico> list = service.findAllMecanicos();
        model.addAttribute("mecanicos", list);
        return "mecanico/list-mecanico";
    }

//    @RequestMapping(path = {"/edit", "/edit{id}"})
//    public String editCategoryById(Model model, @PathVariable("id") Optional<Integer> id)
//            throws RecordNotFoundException {
//        if (id.isPresent()) {
//            Carro entity = service.getCarroById(id.get());
//            model.addAttribute("carro", entity);
//        } else {
//            model.addAttribute("carro", new Carro());
//        }
//        return "carro/add-edit-carro";
//    }
//
//    @RequestMapping(path = "/createCar", method = RequestMethod.POST)
//    public String createOrUpdateCarro(Carro carro) {
//        service.createOrUpdateCarro(carro);
//        return "redirect:/carros"; //REDIRECT: back to previous HTML.
//    }
//
//    @RequestMapping(path = "/delete/{id}")
//    public String deleteCarroById(Model model, @PathVariable("id") Integer id)
//            throws RecordNotFoundException {
//        service.deleteCarroById(id);
//        return "redirect:/carros";
//    }

}
