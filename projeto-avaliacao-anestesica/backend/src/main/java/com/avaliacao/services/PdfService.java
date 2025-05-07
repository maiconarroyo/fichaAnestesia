package main.java.com.avaliacao.services;

import com.avaliacao.models.AvaliacaoAnestesica;
import com.avaliacao.models.Paciente;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfService {
    public byte[] gerarPdf(AvaliacaoAnestesica avaliacao) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        
        document.open();
        
        // Adicionar título
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("RELATÓRIO DE AVALIAÇÃO PRÉ-ANESTÉSICA", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        // Adicionar informações do paciente (crie tabelas conforme o modelo da foto)
        // Exemplo simplificado:
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        addCell(table, "Nome do Paciente:", avaliacao.getPaciente().getNome());
        addCell(table, "Idade:", String.valueOf(avaliacao.getPaciente().getIdade()));
        // Continue com todos os campos necessários...
        
        document.add(table);
        
        // Adicionar seção de avaliação anestésica
        Paragraph subTitle = new Paragraph("AVALIAÇÃO ANESTÉSICA", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
        subTitle.setSpacingBefore(20);
        subTitle.setSpacingAfter(10);
        document.add(subTitle);
        
        PdfPTable anestesiaTable = new PdfPTable(2);
        anestesiaTable.setWidthPercentage(100);
        
        addCell(anestesiaTable, "ASA:", avaliacao.getAsa());
        addCell(anestesiaTable, "Mallampati:", avaliacao.getMallampati());
        // Continue com todos os campos...
        
        document.add(anestesiaTable);
        
        document.close();
        return outputStream.toByteArray();
    }
    
    private void addCell(PdfPTable table, String label, String value) {
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
        
        PdfPCell cellLabel = new PdfPCell(new Phrase(label, boldFont));
        cellLabel.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellValue = new PdfPCell(new Phrase(value != null ? value : "", normalFont));
        cellValue.setBorder(Rectangle.NO_BORDER);
        
        table.addCell(cellLabel);
        table.addCell(cellValue);
    }
}