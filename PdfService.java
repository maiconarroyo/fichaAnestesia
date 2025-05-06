package com.exemplo.formulario.service;

import com.exemplo.formulario.dto.AvaliacaoDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class PdfService {

    public byte[] gerarFichaPdf(AvaliacaoDTO dados) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document doc = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(doc, baos);
            doc.open();

            // Adiciona imagem base
            InputStream is = new ClassPathResource("base.jpg").getInputStream();
            Image bg = Image.getInstance(is.readAllBytes());
            bg.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            bg.setAbsolutePosition(0, 0);
            doc.add(bg);

            PdfContentByte canvas = writer.getDirectContent();
            BaseFont fonte = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            canvas.setFontAndSize(fonte, 12);
            canvas.setColorFill(BaseColor.BLACK);

            // Exemplo de posicionamento (X, Y)
            canvas.beginText();
            canvas.showTextAligned(Element.ALIGN_LEFT, "Nome: " + dados.nome, 50, 770, 0);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Idade: " + dados.idade, 50, 750, 0);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Peso: " + dados.peso + " kg", 50, 730, 0);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Altura: " + dados.altura + " cm", 50, 710, 0);
            canvas.showTextAligned(Element.ALIGN_LEFT, "ASA: " + dados.asa, 50, 690, 0);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Mallampati: " + dados.mallampati, 50, 670, 0);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Cirurgia: " + dados.cirurgia, 50, 650, 0);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Observações: " + dados.observacoes, 50, 630, 0);
            canvas.endText();

            doc.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
