package com.tccfer.application.model.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import com.tccfer.application.controller.dto.orcamento.OrcamentoCadastroDTO;
import com.tccfer.application.controller.dto.orcamento.OrcamentoDTO;
import com.tccfer.application.controller.dto.orcamento.OrcamentoItemDTO;
import com.tccfer.application.model.entity.enuns.SubtipoOrcamento;
import com.tccfer.application.model.entity.enuns.TipoOrcamento;
import com.tccfer.application.model.entity.orcamento.Orcamento;
import com.tccfer.application.model.entity.orcamento.OrcamentoItem;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import com.tccfer.application.model.entity.visitas.VisitaTecnica;
import com.tccfer.application.model.repository.orcamentorepository.OrcamentoRepository;
import com.tccfer.application.model.repository.usuariorepository.PessoaRepository;
import com.tccfer.application.model.repository.visitarepository.VisitaTecnicaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrcamentoService {

    private final OrcamentoRepository orcRepo;
    private final PessoaRepository pessoaRepo;
    private final VisitaTecnicaRepository visitaRepo;

    @Autowired
    private TemplateEngine templateEngine;

    public OrcamentoService(OrcamentoRepository orcRepo,
                            PessoaRepository pessoaRepo,
                            VisitaTecnicaRepository visitaRepo) {
        this.orcRepo = orcRepo;
        this.pessoaRepo = pessoaRepo;
        this.visitaRepo = visitaRepo;
    }

    public OrcamentoDTO criarOrcamento(OrcamentoCadastroDTO dto) {
        // buscar cliente
        Pessoa cliente = pessoaRepo.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // buscar visita, se houver
        VisitaTecnica visita = null;
        if (dto.getVisitaId() != null) {
            visita = visitaRepo.findById(dto.getVisitaId())
                    .orElseThrow(() -> new RuntimeException("Visita não encontrada"));
        }

        // montar entidade Orcamento
        Orcamento orc = new Orcamento();
        orc.setCliente(cliente);
        orc.setVisita(visita);
        orc.setDescricao(dto.getDescricao());
        orc.setTipo(TipoOrcamento.valueOf(dto.getTipo().toUpperCase()));
        orc.setSubtipo(SubtipoOrcamento.valueOf(dto.getSubtipo().toUpperCase()));
        orc.setComMaterial(dto.isComMaterial());

        if (dto.isComMaterial() && dto.getItens() != null) {
            List<OrcamentoItem> items = dto.getItens().stream().map(itemDto -> {
                OrcamentoItem item = new OrcamentoItem();
                item.setOrcamento(orc);
                item.setDescricao(itemDto.getDescricao());
                item.setQuantidade(itemDto.getQuantidade());
                item.setValorUnitario(itemDto.getValorUnitario());
                return item;
            }).collect(Collectors.toList());
            orc.setItens(items);
        }

        // salvar orçamento (itens serão salvos em cascade)
        Orcamento orcamentoSalvo = orcRepo.save(orc);

        return mapToDTO(orcamentoSalvo);
    }

    public List<OrcamentoDTO> listarOrcamentos() {
        return orcRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public OrcamentoDTO buscarPorId(Long id) {
        return orcRepo.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
    }

    public OrcamentoDTO atualizarOrcamento(Long id, OrcamentoCadastroDTO dto) {
        Orcamento orc = orcRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));

        // atualizar campos
        orc.setDescricao(dto.getDescricao());
        orc.setTipo(TipoOrcamento.valueOf(dto.getTipo().toUpperCase()));
        orc.setSubtipo(SubtipoOrcamento.valueOf(dto.getSubtipo().toUpperCase()));
        orc.setComMaterial(dto.isComMaterial());

        // atualizar visita se mudou
        if (dto.getVisitaId() != null) {
            VisitaTecnica visita = visitaRepo.findById(dto.getVisitaId())
                    .orElseThrow(() -> new RuntimeException("Visita não encontrada"));
            orc.setVisita(visita);
        } else {
            orc.setVisita(null);
        }

        // atualizar itens
        orc.getItens().clear();
        if (dto.isComMaterial() && dto.getItens() != null) {
            List<OrcamentoItem> items = dto.getItens().stream().map(itemDto -> {
                OrcamentoItem item = new OrcamentoItem();
                item.setOrcamento(orc);
                item.setDescricao(itemDto.getDescricao());
                item.setQuantidade(itemDto.getQuantidade());
                item.setValorUnitario(itemDto.getValorUnitario());
                return item;
            }).collect(Collectors.toList());
            orc.getItens().addAll(items);
        }

        Orcamento orcamentoSalvo = orcRepo.save(orc);
        return mapToDTO(orcamentoSalvo);
    }

    public void deletarOrcamento(Long id) {
        if (!orcRepo.existsById(id)) {
            throw new RuntimeException("Orçamento não encontrado");
        }
        orcRepo.deleteById(id);
    }

    private OrcamentoDTO mapToDTO(Orcamento orc) {
        OrcamentoDTO dto = new OrcamentoDTO();
        dto.setId(orc.getId());
        dto.setClienteId(orc.getCliente().getId());
        dto.setClienteNome(orc.getCliente().getNome());
        dto.setVisitaId(orc.getVisita() != null ? orc.getVisita().getId() : null);
        dto.setDescricao(orc.getDescricao());
        dto.setTipo(orc.getTipo().name());
        dto.setSubtipo(orc.getSubtipo().name());
        dto.setComMaterial(orc.isComMaterial());

        // Só mapeia itens se for com material
        if (orc.isComMaterial()) {
            List<OrcamentoItemDTO> itensDto = Optional.ofNullable(orc.getItens())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(item -> {
                        OrcamentoItemDTO i = new OrcamentoItemDTO();
                        i.setId(item.getId());
                        i.setDescricao(item.getDescricao());
                        i.setQuantidade(item.getQuantidade());
                        i.setValorUnitario(item.getValorUnitario());
                        return i;
                    })
                    .collect(Collectors.toList());
            dto.setItens(itensDto);
        } else {
            dto.setItens(Collections.emptyList()); // ou null, se tua lógica preferir
        }

        dto.setDataCriacao(orc.getDataCriacao().toString());
        return dto;
    }
    public byte[] gerarPdfOrcamento(Long id) {
        OrcamentoDTO dto = buscarPorId(id); // busca o DTO com os dados

        // Converte data (String ISO) para data formatada
        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_DATE_TIME;
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDateTime dateTime = LocalDateTime.parse(dto.getDataCriacao(), inputFormatter);
        String dataFormatada = dateTime.format(outputFormatter);

        // Calcula o total apenas se houver material
        BigDecimal total = BigDecimal.ZERO;
        if (dto.isComMaterial() && dto.getItens() != null) {
            total = dto.getItens().stream()
                    .map(item -> item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        // Thymeleaf context
        Context context = new Context();
        context.setVariable("orcamento", dto);
        context.setVariable("dataFormatada", dataFormatada);
        context.setVariable("total", total); // <-- agora passando o total pro template

        // Processa o HTML
        String html = templateEngine.process("orcamento", context);

        // Gera PDF
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(baos);
            builder.run();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF do orçamento", e);
        }
    }

}
