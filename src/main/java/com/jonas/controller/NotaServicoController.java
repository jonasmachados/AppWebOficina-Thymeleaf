package com.jonas.controller;

import com.jonas.exception.RecordNotFoundException;
import com.jonas.model.domain.Carro;
import com.jonas.model.domain.Mecanico;
import com.jonas.model.domain.NotaServico;
import com.jonas.model.domain.NotaServicoPDFExporter;
import com.jonas.model.service.NotaServicoService;
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

    @RequestMapping(path = {"/editNFe", "/editNFe{id}"})
    public String editNotaServicoById(Model model, @PathVariable("id") Optional<Integer> id)
            throws RecordNotFoundException {
        if (id.isEmpty()) {

            model.addAttribute("notaServico", new NotaServico());

            List<Mecanico> listMecanico = service.findAllMecanico();
            model.addAttribute("listMecanico", listMecanico);

            List<Carro> listCarro = service.findAllCarros();
            model.addAttribute("listCarro", listCarro);

        } else {
            NotaServico entity = service.getNotaServicoById(id.get());
            model.addAttribute("notaServico", entity);

            List<Mecanico> listMecanico = service.findAllMecanico();
            model.addAttribute("listMecanico", listMecanico);

            List<Carro> listCarro = service.findAllCarros();
            model.addAttribute("listCarro", listCarro);
        }
        return "notaServico/add-edit-nfe";
    }

    @RequestMapping(path = "/createNFe", method = RequestMethod.POST)
    public String createOrUpdateNotaServico(NotaServico notaServico) {
        service.createOrUpdateNotaServico(notaServico);
        return "redirect:/notasServico"; //REDIRECT: back to previous HTML.
    }

    @RequestMapping(path = "/deleteNFe/{id}")
    public String deleteNFeById(Model model, @PathVariable("id") Integer id)
            throws RecordNotFoundException {
        service.deleteNFeById(id);
        return "redirect:/notasServico";
    }
    
     @RequestMapping(value = "/relatorioNfeHTML")
    public String relatorioNfe(Model model) {
        List<NotaServico> list = service.findAllNotasServico();
        model.addAttribute("relatorioNfeHTML", list);
        return "peca/relatorios/HTML-list-nfe";
    }
    
    @GetMapping("/relatorioNfePDF")
    public void relatorioMecanicostoPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=nfe" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<NotaServico> listNfe = service.findAllNotasServico();

        NotaServicoPDFExporter exporter = new NotaServicoPDFExporter(listNfe);
        exporter.export(response);

    }

    //Handling conversor string to a data 
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
    }
}
