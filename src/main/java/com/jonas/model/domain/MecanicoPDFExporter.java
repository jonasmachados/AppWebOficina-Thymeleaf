package com.jonas.model.domain;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jonas, created 16/06/2021
 */
public class MecanicoPDFExporter {

    private List<Mecanico> listMecanicos;

    public MecanicoPDFExporter(List<Mecanico> listMecanicos) {
        this.listMecanicos = listMecanicos;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Mecanico ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("razaoSocial", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("cpf_Cnpj", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("cep", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("endereco", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("numero", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("bairro", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("cidade", font));
        table.addCell(cell);
        
         cell.setPhrase(new Phrase("telefone", font));
        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("dataCriacao", font));
//        table.addCell(cell);
    }

    //HANDLING TABLE
    private void writeTableData(PdfPTable table) {
        for (Mecanico mecanico : listMecanicos) {
            table.addCell(String.valueOf(mecanico.getId()));
            table.addCell(mecanico.getRazaoSocial());
            table.addCell(String.valueOf(mecanico.getCpf_Cnpj()));
             table.addCell(mecanico.getCep());
              table.addCell(mecanico.getEndereco());
             table.addCell(String.valueOf(mecanico.getNumero()));
                table.addCell(mecanico.getBairro());
                   table.addCell(mecanico.getCidade());
                  table.addCell(String.valueOf(mecanico.getTelefone()));
                  
        }
    }

    //HANDLING PARAGRAPH
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Mecanicos", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f, 1.5f, 1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(15);//Put Space betweens p to table

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
