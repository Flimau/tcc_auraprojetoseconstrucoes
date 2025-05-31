package com.tccfer.application.model.service;

import com.tccfer.application.client.ViaCepResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {
    
    public ViaCepResponse buscarEnderecoPorCep(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ViaCepResponse> response = restTemplate.getForEntity(url, ViaCepResponse.class);

            if (response.getBody() == null || response.getBody().getErro() != null) {
                throw new RuntimeException("CEP não encontrado.");
            }

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o CEP", e);
        }
    }
}
