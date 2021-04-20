package com.jonas.controller;

import com.jonas.model.domain.Carro;
import com.jonas.model.service.CarroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "carro/list-car";
    }
}
