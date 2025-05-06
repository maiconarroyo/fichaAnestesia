package com.exemplo.formulario.controller;

import com.exemplo.formulario.dto.AvaliacaoDTO;
import com.exemplo.formulario.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FormularioController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/gerar-pdf")
    public ResponseEntity<byte[]> gerarPdf(@RequestBody AvaliacaoDTO dados) {
        byte[] pdf = pdfService.gerarFichaPdf(dados);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("ficha-anestesica.pdf").build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
