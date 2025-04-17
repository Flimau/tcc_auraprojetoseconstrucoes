package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.ClienteDTO;
import com.tccfer.application.model.entity.Cliente;
import com.tccfer.application.model.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService (ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public ClienteDTO buscarPorId(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return new ClienteDTO(cliente);
    }
}
