// src/main/java/com/tccfer/application/model/service/ContratoService.java
package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.contrato.ContratoDTO;
import com.tccfer.application.controller.dto.contrato.ContratoResumoDTO;
import com.tccfer.application.model.entity.contrato.Contrato;
import com.tccfer.application.model.entity.orcamento.Orcamento;
import com.tccfer.application.model.repository.contratorepository.ContratoRepository;
import com.tccfer.application.model.repository.orcamentorepository.OrcamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço que gerencia criação, atualização, listagem e busca de ContratoEntity.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ContratoService {

    private final ContratoRepository contratoRepo;
    private final OrcamentoRepository orcamentoRepo;

    /**
     * Cria um contrato vinculado a um orçamento existente.
     * Se já existir contrato para esse orçamento, lança exceção.
     */
    public ContratoDTO criarContrato(ContratoDTO dto) {
        Orcamento orc = orcamentoRepo.findById(dto.getOrcamentoId())
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado: ID=" + dto.getOrcamentoId()));

        // Verifica se já existe um contrato para esse orçamento
        if (contratoRepo.findByOrcamentoId(orc.getId()).isPresent()) {
            throw new RuntimeException("Já existe contrato para esse orçamento: " + orc.getId());
        }

        Contrato ent = Contrato.builder()
                .orcamento(orc)
                .dataInicio(dto.getDataInicio())
                .dataFim(dto.getDataFim())
                .valorTotal(dto.getValorTotal())
                .build();

        Contrato salvo = contratoRepo.save(ent);
        return mapToDTO(salvo);
    }

    /**
     * Atualiza um contrato existente (por ID).
     */
    public ContratoDTO atualizarContrato(Long id, ContratoDTO dto) {
        Contrato ent = contratoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado: ID=" + id));

        // Atualiza campos mutáveis
        ent.setDataInicio(dto.getDataInicio());
        ent.setDataFim(dto.getDataFim());
        ent.setValorTotal(dto.getValorTotal());
        Contrato atualizado = contratoRepo.save(ent);

        return mapToDTO(atualizado);
    }

    /**
     * Busca um contrato por ID.
     */
    @Transactional(readOnly = true)
    public ContratoDTO buscarPorId(Long id) {
        Contrato ent = contratoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado: ID=" + id));
        return mapToDTO(ent);
    }

    /**
     * Busca o contrato vinculado a um orçamento específico (se existir).
     */
    @Transactional(readOnly = true)
    public ContratoDTO buscarPorOrcamento(Long orcamentoId) {
        Contrato ent = contratoRepo.findByOrcamentoId(orcamentoId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado para orçamento: " + orcamentoId));
        return mapToDTO(ent);
    }

    /**
     * Lista todos os contratos (resumos).
     */
    @Transactional(readOnly = true)
    public List<ContratoResumoDTO> listarContratosResumo() {
        return contratoRepo.findAll().stream()
                .map(this::mapToResumoDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converte entidade para DTO completo.
     */
    private ContratoDTO mapToDTO(Contrato ent) {
        ContratoDTO dto = new ContratoDTO();
        dto.setId(ent.getId());
        dto.setOrcamentoId(ent.getOrcamento().getId());
        dto.setDataInicio(ent.getDataInicio());
        dto.setDataFim(ent.getDataFim());
        dto.setValorTotal(ent.getValorTotal());
        return dto;
    }

    /**
     * Converte entidade para DTO de resumo (para listagem).
     */
    private ContratoResumoDTO mapToResumoDTO(Contrato ent) {
        // Aproveitamos que orcamento.getCliente() está disponível no Orcamento
        Long clienteId = ent.getOrcamento().getCliente().getId();
        String clienteNome = ent.getOrcamento().getCliente().getNome();
        return new ContratoResumoDTO(
                ent.getId(),
                ent.getOrcamento().getId(),
                clienteId,
                clienteNome
        );
    }
}
