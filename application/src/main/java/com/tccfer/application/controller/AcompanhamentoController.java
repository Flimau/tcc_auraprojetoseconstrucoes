package com.tccfer.application.controller;

import com.tccfer.application.controller.dto.obra.AcompanhamentoCadastroDTO;
import com.tccfer.application.controller.dto.obra.AcompanhamentoDetalhadoDTO;
import com.tccfer.application.controller.dto.obra.AcompanhamentoListagemDTO;
import com.tccfer.application.model.service.AcompanhamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/acompanhamentos")
public class AcompanhamentoController {
    @Autowired
    private AcompanhamentoService acompanhamentoService;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AcompanhamentoCadastroDTO dto) {
        acompanhamentoService.salvar(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @RequestBody AcompanhamentoCadastroDTO dto) {
        acompanhamentoService.editar(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/obra/{obraId}")
    public ResponseEntity<List<AcompanhamentoListagemDTO>> listarPorObra(@PathVariable Long obraId) {
        return ResponseEntity.ok(acompanhamentoService.listarPorObra(obraId));
    }

    @GetMapping("/obra/{obraId}/data/{data}")
    public ResponseEntity<AcompanhamentoCadastroDTO> buscarPorData(
            @PathVariable Long obraId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        AcompanhamentoCadastroDTO dto = acompanhamentoService.buscarPorData(obraId, data);
        return ResponseEntity.ok(dto);
    }

}
