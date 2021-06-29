package com.jonas.controller;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Carro;
import com.jonas.model.service.CarroService;
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
public class CarroController {

    @Autowired
    private CarroService service;

    @RequestMapping(value = "/carros")
    public String findAllCarros(Model model) {
        List<Carro> list = service.findAllCarros();
        model.addAttribute("carros", list);
        return "carro/list-carro";
    }

    @RequestMapping(path = {"/edit", "/edit{id}"})
    public String editCategoryById(Model model, @PathVariable("id") Optional<Integer> id)
            throws RecordNotFoundException {
        if (id.isPresent()) {
            Carro entity = service.getCarroById(id.get());
            model.addAttribute("carro", entity);
        } else {
            model.addAttribute("carro", new Carro());
        }
        return "carro/add-edit-carro";
    }

    @RequestMapping(path = "/createCar", method = RequestMethod.POST)
    public String createOrUpdateCarro(Carro carro) {
        service.createOrUpdateCarro(carro);
        return "redirect:/carros"; //REDIRECT: back to previous HTML.
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteCarroById(Model model, @PathVariable("id") Integer id)
            throws RecordNotFoundException {
        service.deleteCarroById(id);
        return "redirect:/carros";
    }

    //Method to report of all cars
    @RequestMapping(value = "/relatorioCarrosHTML")
    public String relatorioCarrosHTML(Model model) {
        List<Carro> list = service.findAllCarros();
        model.addAttribute("relatorioCarrosHTML", list);
        return "peca/relatorios/HTML-list-carros";
    }

}
