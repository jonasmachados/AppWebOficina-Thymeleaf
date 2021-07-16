package com.jonas.controller;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Carro;
import com.jonas.model.domain.CarroPDFExporter;
import com.jonas.model.domain.PecaPDFExporter;
import com.jonas.model.service.CarroService;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestMapping("/carros")
    public String viewHomePage(Model model) {
        String keyword = null;
        return viewPage(model, 1, "modelo", "asc", keyword);
    }

    @RequestMapping("/pageCarro/{pageNumCarro}")
    public String viewPage(Model model,
            @PathVariable("pageNumCarro") int currentPage,
            @Param("sortField") String sortField,
            @Param("sortDir") String sortDir,
            @Param("keyword") String keyword) {

        Page<Carro> pageCarro = service.listAll(currentPage, sortField, sortDir, keyword);
        long totalItems = pageCarro.getTotalElements();
        int totalPages = pageCarro.getTotalPages();

        List<Carro> listCarro = pageCarro.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", pageCarro.getTotalPages());
        model.addAttribute("totalItems", pageCarro.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("listCarro", listCarro);

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

    //Method to export report of all parts to PDF
    @GetMapping("/relatorioCarrosPDF")
    public void relatorioCarrosPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";

        List<Carro> listCarros = service.findAllCarros();
        CarroPDFExporter exporter = new CarroPDFExporter(listCarros);
        exporter.export(response);
    }

}
