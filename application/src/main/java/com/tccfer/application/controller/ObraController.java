package com.tccfer.application.controller;

import com.tccfer.application.controller.dto.obra.IniciarObraDTO;
import com.tccfer.application.controller.dto.obra.ObraCadastroDTO;
import com.tccfer.application.controller.dto.obra.ObraDetalhadaDTO;
import com.tccfer.application.controller.dto.obra.ObraListagemDTO;
import com.tccfer.application.model.service.ObraService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/obras")
@RequiredArgsConstructor
public class ObraController {

    private final ObraService obraService;

    @PostMapping
    public void cadastrar(@RequestBody ObraCadastroDTO dto) {
        obraService.cadastrar(dto);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody ObraCadastroDTO dto) {
        obraService.atualizar(id, dto);
    }

    @GetMapping("/{id}")
    public ObraDetalhadaDTO buscarPorId(@PathVariable Long id) {
        return obraService.buscarPorId(id);
    }

    @GetMapping
    public List<ObraListagemDTO> listar() {
        return obraService.listar();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        obraService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar-por-cliente")
    public List<ObraListagemDTO> buscarPorCliente(@RequestParam String nomeCliente) {
        return obraService.buscarPorCliente(nomeCliente);
    }

    @PatchMapping("/{id}/iniciar")
    public void iniciarObra(@PathVariable Long id, @RequestBody IniciarObraDTO dto) {
        obraService.iniciarObra(id,dto);
    }

    @PatchMapping("/{id}/finalizar")
    public void finalizarObra(@PathVariable Long id, @RequestBody IniciarObraDTO dto) {
        obraService.finalizarObra(id,dto);
    }
}
