package com.jonas.controller;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Mecanico;
import com.jonas.model.domain.MecanicoPDFExporter;
import com.jonas.model.service.MecanicoService;
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
public class MecanicoController {

    @Autowired
    private MecanicoService service;

    @RequestMapping(value = "/mecanicos")
    public String findAllMecanicos(Model model) {
        List<Mecanico> list = service.findAllMecanicos();
        model.addAttribute("mecanicos", list);
        return "mecanico/list-mecanico";
    }

    @RequestMapping(path = {"/editMecanico", "/editMecanico{id}"})
    public String editMecanicoById(Model model, @PathVariable("id") Optional<Integer> id)
            throws RecordNotFoundException {
        if (id.isPresent()) {
            Mecanico entity = service.getMecanicoById(id.get());
            model.addAttribute("mecanico", entity);
        } else {
            model.addAttribute("mecanico", new Mecanico());
        }
        return "mecanico/add-edit-mecanico";
    }

    @RequestMapping(path = "/createMecanico", method = RequestMethod.POST)
    public String createOrUpdateMecanico(Mecanico mecanico) {
        service.createOrUpdateMecanico(mecanico);
        return "redirect:/mecanicos"; //REDIRECT: back to previous HTML.
    }

    @RequestMapping(path = "/deleteMecanico/{id}")
    public String deleteCarroById(Model model, @PathVariable("id") Integer id)
            throws RecordNotFoundException {
        service.deleteMecanicoById(id);
        return "redirect:/mecanicos";
    }

    //Method to display report of Mechanic
//    @RequestMapping(value = "/relatorioMecanicos")
//    public String relatorioMecanicos(Model model) {
//        List<Mecanico> list = service.findAllMecanicos();
//        model.addAttribute("relatorioMecanicos", list);
//        return "peca/relatorios/HTML-list-mecanicos";
//    }
    @GetMapping("/relatorioMecanicos")
    public void relatorioMecanicostoPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=mecanicos_" + currentDateTime + ".pdf";
        String headerValue = "attachment; filename=mecanicos_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Mecanico> listMecanicos = service.findAllMecanicos();

        MecanicoPDFExporter exporter = new MecanicoPDFExporter(listMecanicos);
        exporter.export(response);

    }

    //Handling conversor string to a data 
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
    }
}
