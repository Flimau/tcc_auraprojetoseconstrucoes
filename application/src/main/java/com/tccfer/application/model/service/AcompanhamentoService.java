package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.obra.AcompanhamentoCadastroDTO;
import com.tccfer.application.controller.dto.obra.AcompanhamentoDetalhadoDTO;
import com.tccfer.application.controller.dto.obra.AcompanhamentoListagemDTO;
import com.tccfer.application.model.entity.enuns.StatusAcompanhamento;
import com.tccfer.application.model.entity.obra.AcompanhamentoDiarioDeObra;
import com.tccfer.application.model.entity.obra.Obra;
import com.tccfer.application.model.repository.obrarepository.AcompanhamentoRepository;
import com.tccfer.application.model.repository.obrarepository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcompanhamentoService {

    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    @Autowired
    private ObraRepository obraRepository;

    public void salvar(AcompanhamentoCadastroDTO dto) {
        AcompanhamentoDiarioDeObra entidade = new AcompanhamentoDiarioDeObra();

        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));

        entidade.setObra(obra);
        entidade.setDataRegistro(dto.getDataRegistro());
        entidade.setTarefas(dto.getTarefas());
        entidade.setTarefasConcluidas(dto.getTarefasConcluidas());
        entidade.setObservacoes(dto.getObservacoes());

        acompanhamentoRepository.save(entidade);
    }

    public void editar(Long id, AcompanhamentoCadastroDTO dto) {
        AcompanhamentoDiarioDeObra entidade = acompanhamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado"));

        entidade.setTarefas(dto.getTarefas());
        entidade.setTarefasConcluidas(dto.getTarefasConcluidas());
        entidade.setObservacoes(dto.getObservacoes());

        acompanhamentoRepository.save(entidade);
    }

    public List<AcompanhamentoListagemDTO> listarPorObra(Long obraId) {
        return acompanhamentoRepository.findByObraId(obraId)
                .stream()
                .map(this::toListagemDTO)
                .collect(Collectors.toList());
    }

    public AcompanhamentoCadastroDTO buscarPorData(Long obraId, LocalDate data) {
        AcompanhamentoDiarioDeObra ent = acompanhamentoRepository
                .findByObraIdAndDataRegistro(obraId, data)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado"));

        AcompanhamentoCadastroDTO dto = new AcompanhamentoCadastroDTO();
        dto.setId(ent.getId());
        dto.setObraId(ent.getObra().getId());
        dto.setDataRegistro(ent.getDataRegistro());
        dto.setTarefas(ent.getTarefas() != null ? ent.getTarefas() : new ArrayList<>());
        dto.setTarefasConcluidas(ent.getTarefasConcluidas() != null ? ent.getTarefasConcluidas() : new ArrayList<>());
        dto.setObservacoes(ent.getObservacoes());

        return dto;
    }


    public AcompanhamentoListagemDTO toListagemDTO(AcompanhamentoDiarioDeObra ent) {
        AcompanhamentoListagemDTO dto = new AcompanhamentoListagemDTO();
        dto.setId(ent.getId());
        dto.setDataRegistro(ent.getDataRegistro());

        var tarefas = ent.getTarefas();
        var concluidas = ent.getTarefasConcluidas();

        StatusAcompanhamento status;

        if (tarefas == null || tarefas.isEmpty()) {
            status = StatusAcompanhamento.VAZIO;
        } else if (concluidas == null || concluidas.isEmpty()) {
            status = StatusAcompanhamento.PREVISTO;
        } else if (concluidas.size() < tarefas.size()) {
            // Agora verificamos se está atrasado
            if (ent.getDataRegistro().isBefore(LocalDate.now())) {
                status = StatusAcompanhamento.ATRASADO;
            } else {
                status = StatusAcompanhamento.PREVISTO;
            }
        } else {
            status = StatusAcompanhamento.CONCLUIDO;
        }

        dto.setStatus(status);
        return dto;
    }

}
