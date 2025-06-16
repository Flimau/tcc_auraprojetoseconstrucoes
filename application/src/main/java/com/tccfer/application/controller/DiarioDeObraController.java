package com.tccfer.application.controller;

import com.tccfer.application.controller.dto.obra.DiarioDeObraCadastroDTO;
import com.tccfer.application.controller.dto.obra.DiarioDeObraDetalhadoDTO;
import com.tccfer.application.controller.dto.obra.DiarioDeObraListagemDTO;
import com.tccfer.application.model.service.DiarioDeObraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diarios")
@RequiredArgsConstructor
public class DiarioDeObraController {

    private final DiarioDeObraService diarioService;

    @PostMapping
    public void cadastrar(@RequestBody DiarioDeObraCadastroDTO dto) {
        diarioService.cadastrar(dto);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody DiarioDeObraCadastroDTO dto) {
        diarioService.atualizar(id, dto);
    }

    @GetMapping("/{id}")
    public DiarioDeObraDetalhadoDTO buscarPorId(@PathVariable Long id) {
        return diarioService.buscarPorId(id);
    }

    @GetMapping("/obra/{obraId}")
    public List<DiarioDeObraDetalhadoDTO> listarPorObra(@PathVariable Long obraId) {
        return diarioService.listarPorObra(obraId);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        diarioService.deletar(id);
    }
}
