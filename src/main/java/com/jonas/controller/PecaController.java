package com.jonas.controller;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.PecaPDFExporter;
import com.jonas.model.domain.Pecas;
import com.jonas.model.service.PecaService;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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

    @RequestMapping(path = {"/editPeca", "/editPeca{id}"})
    public String editPecaById(Model model, @PathVariable("id") Optional<Integer> id)
            throws RecordNotFoundException {
        if (id.isPresent()) {
            Pecas entity = service.getPecasById(id.get());
            model.addAttribute("peca", entity);
        } else {
            model.addAttribute("peca", new Pecas());
        }
        return "peca/add-edit-pecas";
    }

    @RequestMapping(path = "/createPeca", method = RequestMethod.POST)
    public String createOrUpdatePecas(Pecas peca) {
        service.createOrUpdatePecas(peca);
        return "redirect:/pecas"; //REDIRECT: back to previous HTML.
    }

    @RequestMapping(path = "/deletePeca/{id}")
    public String deletePecaById(Model model, @PathVariable("id") Integer id)
            throws RecordNotFoundException {
        service.deletePecaById(id);
        return "redirect:/pecas";
    }

    @RequestMapping(value = "/relatorioPecas")
    public String relatorioPecas(Model model) {
        List<Pecas> list = service.findAllPecas();
        model.addAttribute("relatorioPecas", list);
        return "peca/relatorios/HTML-list-pecas";
    }

    //Method to export report of all parts to PDF
    @GetMapping("/relatorioPecasPDF")
    public void relatorioMecanicostoPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pecas" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Pecas> listPecas = service.findAllPecas();

        PecaPDFExporter exporter = new PecaPDFExporter(listPecas);
        exporter.export(response);
    }

    //Handling conversor string to a data 
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
    }

}
