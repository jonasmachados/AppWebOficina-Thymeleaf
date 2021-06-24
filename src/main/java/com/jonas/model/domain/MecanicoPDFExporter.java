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
        cell.setBackgroundColor(new java.awt.Color(0x8F, 0xBC, 0x8B));
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Razão Social", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("CPF/CNPJ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("CEP", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Endereco", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nº", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Bairro", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cidade", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Telefone", font));
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
        //Font of H1(p1)
        Font fontP1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontP1.setSize(18);
        fontP1.setColor(Color.BLACK);

        Font fontP2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontP2.setSize(14);
        fontP2.setColor(Color.BLACK);

        Paragraph p = new Paragraph("APP WEB OFICINA", fontP1);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setLeading(0);
        Paragraph p2 = new Paragraph("Relatório de Peças", fontP2);
        p2.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);
        document.add(p2);
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f, 3.5f, 1.5f, 3.0f, 3.0f, 3.0f});
        table.setSpacingBefore(15);//Put Space betweens p to table

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
