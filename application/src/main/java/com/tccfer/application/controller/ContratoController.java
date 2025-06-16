package com.tccfer.application.controller;

import com.tccfer.application.controller.dto.contrato.ContratoDTO;
import com.tccfer.application.controller.dto.contrato.ContratoResumoDTO;
import com.tccfer.application.model.service.ContratoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContratoController {

    private final ContratoService contratoService;

    /** Lista todos contratos (resumos) */
    @GetMapping("/api/contrato")
    public ResponseEntity<List<ContratoResumoDTO>> listarContratosResumo() {
        List<ContratoResumoDTO> lista = contratoService.listarContratosResumo();
        return ResponseEntity.ok(lista);
    }

    /** Busca contrato completo por ID de contrato */
    @GetMapping("/api/contrato/{id}")
    public ResponseEntity<ContratoDTO> buscarContratoPorId(@PathVariable Long id) {
        ContratoDTO dto = contratoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /** Busca contrato associado a um orçamento (se existir) */
    @GetMapping("/api/orcamento/{orcamentoId}/contrato")
    public ResponseEntity<ContratoDTO> buscarPorOrcamento(@PathVariable Long orcamentoId) {
        ContratoDTO dto = contratoService.buscarPorOrcamento(orcamentoId);
        return ResponseEntity.ok(dto);
    }

    /** Cria um novo contrato para um orçamento */
    @PostMapping("/api/contrato")
    public ResponseEntity<ContratoDTO> criarContrato(@RequestBody ContratoDTO dto) {
        ContratoDTO criado = contratoService.criarContrato(dto);
        return ResponseEntity
                .created(URI.create("/api/contrato/" + criado.getId()))
                .body(criado);
    }

    /** Atualiza um contrato existente */
    @PutMapping("/api/contrato/{id}")
    public ResponseEntity<ContratoDTO> atualizarContrato(
            @PathVariable Long id,
            @RequestBody ContratoDTO dto) {
        ContratoDTO atualizado = contratoService.atualizarContrato(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /** Deleta um contrato existente */
    @DeleteMapping("/api/contrato/{id}")
    public ResponseEntity<Void> deletarContrato(@PathVariable Long id) {
        contratoService.deletarContrato(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/contrato/{id}/pdf")
    public ResponseEntity<byte[]> gerarPdfContrato(@PathVariable Long id) {
        byte[] pdfBytes = contratoService.gerarPdfContrato(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("contrato.pdf").build());

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfBytes);
    }

}
