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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
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

    @RequestMapping("/mecanicos")
    public String viewHomePage(Model model) {
        String keyword = null;
        return viewPage(model, 1, "razaoSocial", "asc", keyword);
    }

    @RequestMapping("/page/{pageNum}")
    public String viewPage(Model model,
            @PathVariable("pageNum") int currentPage,
            @Param("sortField") String sortField,
            @Param("sortDir") String sortDir,
            @Param("keyword") String keyword) {

        Page<Mecanico> page = service.listAll(currentPage, sortField, sortDir, keyword);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Mecanico> listMecanico = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("listMecanico", listMecanico);

        return "mecanico/list-mecanico";
    }
    
    @RequestMapping(path = {"/editMecanico", "/editMecanico{id}"})
    public String editCategoryById(Model model, @PathVariable("id") Optional<Integer> id)
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

    @RequestMapping(value = "/relatorioMecanicosHTML")
    public String relatorioPecas(Model model) {
        List<Mecanico> list = service.findAllMecanicos();
        model.addAttribute("relatorioMecanicosHTML", list);
        return "peca/relatorios/HTML-list-mecanicos";
    }

    @GetMapping("/relatorioMecanicosPDF")
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
