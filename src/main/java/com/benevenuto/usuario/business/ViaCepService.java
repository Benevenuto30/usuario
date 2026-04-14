package com.benevenuto.usuario.business;

import com.benevenuto.usuario.infrastructure.client.ViaCepClient;
import com.benevenuto.usuario.infrastructure.client.ViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public ViaCepDTO buscarCep(String cep) {
        cep = processarCep(cep);
        return viaCepClient.buscarDadosDeCep(cep);
    }

    private String processarCep(String cep) {
        String cepFormatado = cep.replaceAll("[^0-9]", "");
        if(!cepFormatado.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP inválido: " + cep);
        }
        return cepFormatado;
    }
}
