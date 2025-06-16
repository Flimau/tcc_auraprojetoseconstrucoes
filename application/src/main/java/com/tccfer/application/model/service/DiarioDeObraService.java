package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.obra.DiarioDeObraCadastroDTO;
import com.tccfer.application.controller.dto.obra.DiarioDeObraDetalhadoDTO;
import com.tccfer.application.controller.dto.obra.DiarioDeObraListagemDTO;
import com.tccfer.application.model.entity.obra.DiarioDeObra;
import com.tccfer.application.model.entity.obra.Obra;
import com.tccfer.application.model.repository.obrarepository.DiarioDeObraRepository;
import com.tccfer.application.model.repository.obrarepository.ObraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiarioDeObraService {

    private final DiarioDeObraRepository diarioRepo;
    private final ObraRepository obraRepo;

    public void cadastrar(DiarioDeObraCadastroDTO dto) {
        Obra obra = obraRepo.findById(dto.getObraId())
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));

        DiarioDeObra diario = new DiarioDeObra();
        diario.setObraId(obra.getId());
        diario.setDataRegistro(dto.getDataRegistro());
        diario.setObservacoes(dto.getObservacoes());

        diarioRepo.save(diario);
    }

    public void atualizar(Long id, DiarioDeObraCadastroDTO dto) {
        DiarioDeObra diario = diarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diário não encontrado"));

        diario.setDataRegistro(dto.getDataRegistro());
        diario.setObservacoes(dto.getObservacoes());

        diarioRepo.save(diario);
    }

    public DiarioDeObraDetalhadoDTO buscarPorId(Long id) {
        DiarioDeObra diario = diarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diário não encontrado"));

        DiarioDeObraDetalhadoDTO dto = new DiarioDeObraDetalhadoDTO();
        dto.setId(diario.getId());
        dto.setDataRegistro(diario.getDataRegistro());
        dto.setObservacoes(diario.getObservacoes());

        return dto;
    }

    public List<DiarioDeObraDetalhadoDTO> listarPorObra(Long obraId) {
        return diarioRepo.findByObraId(obraId).stream().map(diario -> {
            DiarioDeObraDetalhadoDTO dto = new DiarioDeObraDetalhadoDTO();
            dto.setId(diario.getId());
            dto.setDataRegistro(diario.getDataRegistro());
            dto.setObservacoes(diario.getObservacoes());
            return dto;
        }).collect(Collectors.toList());
    }

    public void deletar(Long id) {
        DiarioDeObra diario = diarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diário não encontrado"));
        diarioRepo.delete(diario);
    }

}
