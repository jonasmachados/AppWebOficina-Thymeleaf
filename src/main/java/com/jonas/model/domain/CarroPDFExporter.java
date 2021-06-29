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
public class CarroPDFExporter {

    private List<Carro> listCarros;

    public CarroPDFExporter(List<Carro> listCarros) {
        this.listCarros = listCarros;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new java.awt.Color(0x8F, 0xBC, 0x8B));
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Modelo", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Marca", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ano", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Chassi", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Renavan", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cor", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Combustivel", font));
        table.addCell(cell);
        
    }

    //HANDLING TABLE
    private void writeTableData(PdfPTable table) {
        for (Carro carro : listCarros) {
            table.addCell(String.valueOf(carro.getId()));
            table.addCell(carro.getModelo());
            table.addCell(carro.getMarca());
            table.addCell(String.valueOf(carro.getAno()));
            table.addCell(carro.getChassi());
            table.addCell(carro.getRenavan());
            table.addCell(carro.getCor());
            table.addCell(carro.getCombustivel());
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
        Paragraph p2 = new Paragraph("Relatório de Carros", fontP2);
        p2.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);
        document.add(p2);
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.5f, 2.5f, 3.5f, 3.5f, 2.5f, 3.5f});
        table.setSpacingBefore(15);//Put Space betweens p to table

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
