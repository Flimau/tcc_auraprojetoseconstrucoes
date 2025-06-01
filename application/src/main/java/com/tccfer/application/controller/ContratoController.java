package com.tccfer.application.controller;

import com.tccfer.application.model.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orcamento")
public class ContratoController {

    private final ContratoService contratoService;

    @Autowired
    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    /**
     * Gera e retorna o PDF do contrato para o orçamento {id}.
     * GET /api/orcamento/{id}/contrato
     */
    @GetMapping("/{id}/contrato")
    public ResponseEntity<byte[]> baixarContrato(@PathVariable Long id) {
        byte[] pdfBytes = contratoService.gerarContratoPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=contrato_orcamento_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
